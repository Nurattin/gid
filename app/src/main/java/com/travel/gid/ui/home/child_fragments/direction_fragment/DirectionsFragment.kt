package com.travel.gid.ui.home.child_fragments.direction_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.travel.gid.R
import com.travel.gid.databinding.FragmentVpHomeToursBinding
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.home.adapters.UpcomingToursAdapter
import com.travel.gid.ui.home.child_fragments.direction_fragment.adapter.DirectionsListAdapter
import com.travel.gid.ui.home.child_fragments.direction_fragment.view_model.DirectionViewModel
import com.travel.gid.utils.SpaceItemDecoration
import com.travel.gid.ui.home.utils.StopRefreshCallback
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.shimmerlayout.ShimmerLayout
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class DirectionsFragment : Fragment() {

    private lateinit var binding: FragmentVpHomeToursBinding
    private val viewModel: DirectionViewModel by viewModels()
    private lateinit var skeletonLayout: LinearLayout
    private lateinit var shimmer: ShimmerLayout
    private var stopRefreshCallback: StopRefreshCallback? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVpHomeToursBinding.inflate(inflater, container, false)
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        skeletonLayout.orientation = LinearLayout.HORIZONTAL
        shimmer = binding.skeletonLayout.shimmerSkeleton
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        setupUpcomingRecyclerView()

        binding.apply {
            showAllTour.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_tourFragment)
            }
            showAllDirection.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_directionListFragment)
            }
        }
    }


    private fun setupRecyclerView() {
        binding.run {
            if (viewModel.directionsList.value == null) showProgressBar()
            val adapter = DirectionsListAdapter()
            similarToursRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            similarToursRecyclerView.adapter = adapter

            viewModel.directionsList.observe(viewLifecycleOwner, Observer {
                it.body()?.data?.let { data ->
                    adapter.data = data
                }
                stopProgressBar()

            })


            adapter.setOnTourClickListener {
                findNavController().navigate(
                    R.id.directionDetailFragment,
                    DirectionDetailFragmentArgs(it.id).toBundle()
                )
            }

            similarToursRecyclerView.clipToPadding = false
            similarToursRecyclerView.addItemDecoration(
                SpaceItemDecoration(
                    space = 50,
                    orientation = SpaceItemDecoration.Orientation.HORIZONTAL
                )
            )
        }
    }

    private fun setupUpcomingRecyclerView() {
        binding.run {
            upcomingToursRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = UpcomingToursAdapter()
            upcomingToursRecyclerView.adapter = adapter
            upcomingToursRecyclerView.clipToPadding = false
        }
    }

    private fun showProgressBar() {
        showSkeleton(true)
    }

    private fun stopProgressBar() {
        animateReplaceSkeleton(binding.similarToursRecyclerView)
        stopRefreshCallback?.invoke()
    }

    private fun showSkeleton(show: Boolean) {
        if (show) {
            binding.similarToursRecyclerView.visibility = View.GONE
            if (skeletonLayout.isEmpty()) {
                for (i in 0..2) {
                    val rowLayout =
                        layoutInflater.inflate(
                            R.layout.skeleton_direction_tour_item,
                            null
                        ) as ViewGroup
                    skeletonLayout.addView(rowLayout)
                }
            }
            shimmer.visibility = View.VISIBLE
            shimmer.startShimmerAnimation()
            skeletonLayout.visibility = View.VISIBLE
            skeletonLayout.bringToFront()
        } else {
            shimmer.stopShimmerAnimation()
            shimmer.visibility = View.GONE
        }
    }

    private fun animateReplaceSkeleton(listView: View) {
        Timer().schedule(1000) {
            activity?.runOnUiThread {
                listView.visibility = View.VISIBLE
                showSkeleton(
                    false,
                )
            }
        }
    }

    fun refreshData() {
        showProgressBar()
        viewModel.getDirectionList()

    }

    fun setOnStopRefreshCallBack(callBack: StopRefreshCallback) {
        stopRefreshCallback = callBack
    }
}

