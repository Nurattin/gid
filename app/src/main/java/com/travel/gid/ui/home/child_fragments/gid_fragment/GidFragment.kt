package com.travel.gid.ui.home.child_fragments.gid_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.travel.gid.databinding.FragmentGidBinding
import com.travel.gid.ui.home.child_fragments.gid_fragment.adapter.GidAdapter
import com.travel.gid.ui.home.child_fragments.gid_fragment.view_model.GidViewModel
import com.travel.gid.ui.home.utils.ShowErrorCallback
import com.travel.gid.ui.home.utils.StopRefreshCallback
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.shimmerlayout.ShimmerLayout
import java.util.*
import kotlin.concurrent.schedule


@AndroidEntryPoint
class GidFragment : Fragment() {
    private var stopRefreshCallback: StopRefreshCallback? = null
    private var showErrorCallback: ShowErrorCallback? = null
    private lateinit var skeletonLayout: LinearLayout
    private lateinit var shimmer: ShimmerLayout
    private val viewModel: GidViewModel by viewModels()
    private lateinit var binding: FragmentGidBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGidBinding.inflate(inflater, container, false)
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        shimmer = binding.skeletonLayout.shimmerSkeleton
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.getGidList()
        showProgressBar()
        viewModel.gidList.observe(viewLifecycleOwner) {
            it.body()?.data.let { data ->
                val adapter = GidAdapter()
                if (!data.isNullOrEmpty()) adapter.data = data
                binding.run {
                    gidRecycler.adapter = adapter
                }
                stopProgressBar()
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            showErrorCallback?.invoke()
        }
    }

    private fun showProgressBar() {
        showSkeleton(true)
    }

    private fun stopProgressBar() {
        animateReplaceSkeleton(binding.gidRecycler)
        stopRefreshCallback?.invoke()
    }

    private fun showSkeleton(show: Boolean) {
        if (show) {
            binding.gidRecycler.visibility = View.GONE
            if (skeletonLayout.isEmpty()) {
                for (i in 0..2) {
                    val rowLayout =
                        layoutInflater.inflate(
                            com.travel.gid.R.layout.skeleton_item_gid,
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
        viewModel.getGidList()

    }

    fun setOnStopRefreshCallBack(callBack: StopRefreshCallback) {
        stopRefreshCallback = callBack
    }

    fun setOnShowErrorCallback(callback: ShowErrorCallback){
        showErrorCallback = callback
    }
}


