package com.travel.gid.ui.home.child_fragments.direction_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.databinding.FragmentVpHomeToursBinding
import com.travel.gid.ui.base.BaseFragment
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.home.adapters.UpcomingToursAdapter
import com.travel.gid.ui.home.child_fragments.direction_fragment.adapter.DirectionsListAdapter
import com.travel.gid.ui.home.child_fragments.direction_fragment.view_model.DirectionViewModel
import com.travel.gid.utils.SpaceItemDecoration
import com.travel.gid.ui.home.utils.StopRefreshCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectionsFragment : BaseFragment<FragmentVpHomeToursBinding>() {
    private val adapterDirection = DirectionsListAdapter()
    private val adapterUpcomingTours = UpcomingToursAdapter()
    private val viewModel: DirectionViewModel by viewModels()
    private var stopRefreshCallback: StopRefreshCallback? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVpHomeToursBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainContent = binding.mainContent
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        skeletonLayout!!.orientation = LinearLayout.HORIZONTAL
        errorInternetContainer = binding.errorInternetContainer
        recyclerView = binding.similarToursRecyclerView
        shimmer = binding.skeletonLayout.shimmerSkeleton
        itemLayoutSkeleton = R.layout.skeleton_direction_tour_item

        showSkeleton()
        setupRecyclerView()
        setupUpcomingRecyclerView()

        binding.apply {
            showAllTour.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_tourFragment, null, defNavOption)
            }
            showAllDirection.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_directionListFragment, null, defNavOption)
            }
            refresh.setOnClickListener {
                refreshData
            }
        }
        viewModel.apply {
            error.observe(viewLifecycleOwner) {
                showError()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.similarToursRecyclerView.run {

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDirection

            viewModel.directionsList.observe(viewLifecycleOwner, Observer {
                it.body()?.data?.let { data ->
                    adapterDirection.data = data
                }
                stopSkeleton()

            })
            adapterDirection.setOnTourClickListener {
                navController.navigate(
                    R.id.directionDetailFragment,
                    DirectionDetailFragmentArgs(it.id).toBundle(),
                    defNavOption,
                )
            }

            clipToPadding = false
            addItemDecoration(
                SpaceItemDecoration(
                    space = 50,
                    orientation = SpaceItemDecoration.Orientation.HORIZONTAL
                )
            )
        }
    }


    private fun setupUpcomingRecyclerView() {
        binding.upcomingToursRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterUpcomingTours
            clipToPadding = false
        }
    }

    override fun stopSkeleton() {
        stopRefreshCallback?.invoke()
        super.stopSkeleton()
    }

    fun refreshData() {
        showSkeleton()
        viewModel.getDirectionList()
        viewModel.getTours()
    }

    fun setOnStopRefreshCallBack(callBack: StopRefreshCallback) {
        stopRefreshCallback = callBack
    }
}

