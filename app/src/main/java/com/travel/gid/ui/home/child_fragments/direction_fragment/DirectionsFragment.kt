package com.travel.gid.ui.home.child_fragments.direction_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.Direction
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.databinding.FragmentVpHomeToursBinding
import com.travel.gid.ui.delegation.Error
import com.travel.gid.ui.delegation.ErrorImpl
import com.travel.gid.ui.delegation.Skeleton
import com.travel.gid.ui.delegation.SkeletonImpl
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.home.adapters.UpcomingToursAdapter
import com.travel.gid.ui.home.child_fragments.direction_fragment.adapter.DirectionsListAdapter
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.ConnectionLiveData
import com.travel.gid.utils.checkInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DirectionsFragment :
    Fragment(), Error by ErrorImpl(), Skeleton by SkeletonImpl() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData
    private lateinit var binding: FragmentVpHomeToursBinding
    private val adapterDirection = DirectionsListAdapter()
    private val adapterUpcomingTours = UpcomingToursAdapter()
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVpHomeToursBinding.inflate(inflater, container, false)
        binding.recyclerGidList.adapter = adapterDirection
        binding.upcomingToursRecyclerView.adapter = adapterUpcomingTours
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        networkConnection.observe(viewLifecycleOwner) {
            when (it) {
                true -> getDataForUpcomingToursRecyclerView()
                false -> {}
            }
        }

        with(binding) {

            showAllTour.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_tourFragment,
                    null,
                )
            }
            showAllDirection.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_directionListFragment,
                    null,
                )
            }
        }

        viewModel.swipeProgress.observe(viewLifecycleOwner) {
            when (it) {
                true -> getDataForUpcomingToursRecyclerView()
                false -> {}
            }
        }

        viewModel.directionsList.observe(viewLifecycleOwner) { result ->

            when (result) {
                is Result.Success -> showDirectionList(result.asSuccess().value)

                is Result.Failure<*> -> {
                    result.asFailure().error.let { throwable ->
                        viewModel.swipeProgress.value = false
                        showError(
                            mainContainer = binding.mainContent,
                            errorContainer = binding.errorInternetContainer,
                            throwable = throwable ?: Throwable("Unknown error"),
                            actionOnErrorButton = { getDataForUpcomingToursRecyclerView() }
                        )
                    }
                }
            }
            viewModel.swipeProgress.value = false
        }
    }

    private fun showDirectionList(value: Direction) {
        binding.recyclerGidList.run {
            value.data.let { data ->
                adapterDirection.data = data
            }
            clipToPadding = false
        }
        adapterDirection.setOnTourClickListener {
            findNavController().navigate(
                R.id.directionDetailFragment,
                DirectionDetailFragmentArgs(it.id).toBundle(),
                null
            )
        }
        stopSkeletonForSimilarToursRecyclerView()
    }


    private fun showSimilarToursRecyclerView() {

    }


    private fun showUpcomingToursRecyclerView() {
        binding.upcomingToursRecyclerView.run {
            adapter = adapterUpcomingTours
            clipToPadding = false
        }
        stopSkeletonForUpcomingToursRecyclerView()
    }

    private fun getDataForUpcomingToursRecyclerView() {
        networkConnection.checkInternetConnection(
            actionOnInternetConnection = {
                if (binding.errorInternetContainer.isVisible) hideError()
                showSkeletonForUpcomingToursRecyclerView()
                viewModel.getDirectionList()
            },
            actionOnInternetDisconnection = {
                viewModel.swipeProgress.value = false
                if (!binding.errorInternetContainer.isVisible) {
                    showError(
                        mainContainer = binding.mainContent,
                        errorContainer = binding.errorInternetContainer,
                        actionOnErrorButton = { getDataForUpcomingToursRecyclerView() }
                    )
                }
            }
        )
    }

    private fun stopSkeletonForSimilarToursRecyclerView() {
        stopSkeleton(
            recyclerView = binding.recyclerGidList,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
        )
    }


    private fun showSkeletonForUpcomingToursRecyclerView() {
        showSkeleton(
            context = requireContext(),
            recyclerView = binding.recyclerGidList,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
            orientation = LinearLayout.HORIZONTAL,
            item = R.layout.skeleton_direction_tour_item,
        )
    }

    private fun hideError() {
        hideError(
            mainContainer = binding.mainContent,
            errorContainer = binding.errorInternetContainer
        )
    }

    private fun stopSkeletonForUpcomingToursRecyclerView() {
    }

}

