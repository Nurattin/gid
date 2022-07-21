package com.travel.gid.ui.home.child_fragments.gid_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.travel.gid.R
import com.travel.gid.databinding.FragmentGidBinding
import com.travel.gid.ui.base.BaseFragment
import com.travel.gid.ui.home.child_fragments.gid_fragment.adapter.GidAdapter
import com.travel.gid.ui.home.child_fragments.gid_fragment.view_model.GidViewModel
import com.travel.gid.ui.home.utils.StopRefreshCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GidFragment : BaseFragment<FragmentGidBinding>() {
    private var stopRefreshCallback: StopRefreshCallback? = null
    private val viewModel: GidViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGidBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainContent = binding.mainContent
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        errorInternetContainer = binding.errorInternetContainer
        recyclerView = binding.gidRecycler
        shimmer = binding.skeletonLayout.shimmerSkeleton
        itemLayoutSkeleton = R.layout.skeleton_item_gid

        showSkeleton()
        viewModel.gidList.observe(viewLifecycleOwner) {
            it.body()?.data.let { data ->
                val adapter = GidAdapter()
                if (!data.isNullOrEmpty()) adapter.data = data
                binding.run {
                    gidRecycler.adapter = adapter
                }
                stopSkeleton()
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            showError()
        }
        binding.refresh.setOnClickListener {
            refreshData()
        }
    }
    override fun stopSkeleton() {
        stopRefreshCallback?.invoke()
        super.stopSkeleton()
    }

    override fun showError() {
        stopRefreshCallback?.invoke()
        super.showError()
    }

    fun refreshData() {
        showSkeleton()
        viewModel.getGidList()
    }
    fun setOnStopRefreshCallBack(callBack: StopRefreshCallback) {
        stopRefreshCallback = callBack
    }
}


