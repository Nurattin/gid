package com.travel.gid.ui.home.child_fragments.event_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.travel.gid.R
import com.travel.gid.databinding.FragmentEventBinding
import com.travel.gid.ui.home.child_fragments.event_fragment.adapter.EventAdapter
import com.travel.gid.ui.home.child_fragments.event_fragment.view_model.EventViewModel
import com.travel.gid.ui.home.utils.ShowErrorCallback
import com.travel.gid.ui.home.utils.StopRefreshCallback
import com.travel.gid.utils.makeGone
import com.travel.gid.utils.stopRefreshed
import com.travel.gid.utils.makeVisibly
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.shimmerlayout.ShimmerLayout


@AndroidEntryPoint
class EventFragment : Fragment() {

    lateinit var binding: FragmentEventBinding
    private var stopRefreshCallback: StopRefreshCallback? = null
    private val viewModel: EventViewModel by viewModels()
    private lateinit var skeletonLayout: LinearLayout
    private lateinit var shimmer: ShimmerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        binding = FragmentEventBinding.bind(view)
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        shimmer = binding.skeletonLayout.shimmerSkeleton
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressBar()
        val eventAdapter = EventAdapter()
        binding.eventRecycler.adapter = eventAdapter
        binding.eventRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel.event.observe(viewLifecycleOwner) { it ->
            it.body()?.data?.let {
                eventAdapter.data = it
            }
            stopProgressBar()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showError()
            stopRefreshCallback?.invoke()
        }
        binding.refresh.setOnClickListener {
            refreshData()
        }
    }


    private fun showProgressBar() {
        showSkeleton(true)
    }

    private fun stopProgressBar() {
        animateReplaceSkeleton(binding.eventRecycler)
        with(binding) {
            mainContent.makeVisibly()
            errorInternetContainer.makeGone()
        }
        stopRefreshCallback?.invoke()
    }

    private fun showSkeleton(show: Boolean) {
        if (show) {
            binding.eventRecycler.makeGone()
            if (skeletonLayout.isEmpty()) {
                for (i in 0..2) {
                    val rowLayout =
                        layoutInflater.inflate(
                            R.layout.skeleton_item_tour_list,
                            null
                        ) as ViewGroup
                    skeletonLayout.addView(rowLayout)
                }
            }
            shimmer.makeVisibly()
            shimmer.startShimmerAnimation()
            skeletonLayout.makeVisibly()
            skeletonLayout.bringToFront()
        } else {
            shimmer.stopShimmerAnimation()
            shimmer.makeGone()
        }
    }

    private fun animateReplaceSkeleton(listView: View) {
        listView.makeVisibly()
        showSkeleton(
            false,
        )
    }

    fun refreshData() {
        showProgressBar()
        viewModel.getEvent()
    }

    fun setOnStopRefreshCallBack(callBack: StopRefreshCallback) {
        stopRefreshCallback = callBack
    }

    private fun showError() {
        with(binding) {
            mainContent.makeGone()
            errorInternetContainer.makeVisibly()
        }
        stopRefreshCallback?.invoke()
    }
}