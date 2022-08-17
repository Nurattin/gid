package com.travel.gid.ui.home.child_fragments.event_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.travel.gid.R
import com.travel.gid.data.models.Events
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.databinding.FragmentEventBinding
import com.travel.gid.ui.delegation.Error
import com.travel.gid.ui.delegation.ErrorImpl
import com.travel.gid.ui.delegation.Skeleton
import com.travel.gid.ui.delegation.SkeletonImpl
import com.travel.gid.ui.home.child_fragments.event_fragment.adapter.EventAdapter
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.ConnectionLiveData
import com.travel.gid.utils.checkInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint

class EventFragment : Fragment(),
    Error by ErrorImpl(),
    Skeleton by SkeletonImpl() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData

    private lateinit var binding: FragmentEventBinding
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private val adapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        binding.eventRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnection.observe(viewLifecycleOwner) {
            when (it) {
                true -> getData()
                false -> {}
            }
        }

        viewModel.swipeProgress.observe(viewLifecycleOwner) {
            when (it) {
                true -> getData()
                false -> {}
            }
        }

        viewModel.events.observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Success -> showEventList(result.asSuccess().value)

                is Result.Failure<*> -> {
                    result.asFailure().error.let { throwable ->
                        showError(
                            mainContainer = binding.mainContent,
                            errorContainer = binding.errorInternetContainer,
                            throwable = throwable ?: Throwable("Unknown error"),
                            actionOnErrorButton = { getData() }
                        )
                    }
                }
            }
            stopSkeletonForEventList()
            viewModel.swipeProgress.value = false
        }

    }

    private fun getData() {
        networkConnection.checkInternetConnection(actionOnInternetConnection = {
            if (binding.errorInternetContainer.isVisible) hideError()
            showSkeletonForEventList()
            viewModel.getEvent()
        }, actionOnInternetDisconnection = {
            viewModel.swipeProgress.value = false
            if (!binding.errorInternetContainer.isVisible) {
                showError(
                    mainContainer = binding.mainContent,
                    errorContainer = binding.errorInternetContainer,
                    actionOnErrorButton = { getData() }
                )
            }
        })
    }

    private fun showEventList(value: Events) {

        value.data.let { data ->
            adapter.data = data
            binding.run {
                eventRecycler.adapter = adapter
            }
        }
    }

    private fun showSkeletonForEventList() {
        showSkeleton(
            context = requireContext(),
            recyclerView = binding.eventRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
            orientation = LinearLayout.VERTICAL,
            item = R.layout.skeleton_item_tour_list
        )
    }

    private fun stopSkeletonForEventList() {
        stopSkeleton(
            recyclerView = binding.eventRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
        )
    }

    private fun hideError() {
        hideError(
            mainContainer = binding.mainContent,
            errorContainer = binding.errorInternetContainer
        )
    }
}