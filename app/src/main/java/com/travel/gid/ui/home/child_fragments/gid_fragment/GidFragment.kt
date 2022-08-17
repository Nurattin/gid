package com.travel.gid.ui.home.child_fragments.gid_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.travel.gid.R
import com.travel.gid.data.models.Gid
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.databinding.FragmentGidBinding
import com.travel.gid.ui.delegation.Error
import com.travel.gid.ui.delegation.ErrorImpl
import com.travel.gid.ui.delegation.Skeleton
import com.travel.gid.ui.delegation.SkeletonImpl
import com.travel.gid.ui.home.child_fragments.gid_fragment.adapter.GidAdapter
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.ConnectionLiveData
import com.travel.gid.utils.checkInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GidFragment : Fragment(),
    Error by ErrorImpl(),
    Skeleton by SkeletonImpl() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData
    lateinit var binding: FragmentGidBinding
    private val viewModel: HomeViewModel by viewModels({ requireParentFragment() })
    private lateinit var adapterGid: GidAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGidBinding.inflate(inflater, container, false)
        adapterGid = GidAdapter()
        binding.gidRecycler.adapter = adapterGid
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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


        viewModel.gidList.observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Success -> showListGid(result.asSuccess().value)

                is Result.Failure<*> -> {
                    result.asFailure().error.let { throwable ->
                        viewModel.swipeProgress.value = false
                        showError(
                            mainContainer = binding.mainContent,
                            errorContainer = binding.errorInternetContainer,
                            throwable = throwable ?: Throwable("Unknown error"),
                            actionOnErrorButton = { getData() }
                        )
                    }
                }
            }
            stopSkeleton()
            viewModel.swipeProgress.value = false
        }
    }


    private fun showListGid(result: Gid) {
        result.data.let { gidList ->
            binding.run {
                adapterGid.data = gidList
            }
        }
    }


    private fun getData() {
        networkConnection.checkInternetConnection(
            actionOnInternetConnection = {
                if (binding.errorInternetContainer.isVisible) hideError()
                showSkeleton()
                viewModel.getGidList()
            },
            actionOnInternetDisconnection = {
                viewModel.swipeProgress.value = false
                if (!binding.errorInternetContainer.isVisible) {
                    showError(
                        mainContainer = binding.mainContent,
                        errorContainer = binding.errorInternetContainer,
                        actionOnErrorButton = { getData() }
                    )
                }
            }
        )
    }

    private fun hideError() {
        hideError(
            mainContainer = binding.mainContent,
            errorContainer = binding.errorInternetContainer
        )
    }

    private fun showSkeleton() {
        showSkeleton(
            context = requireContext(),
            recyclerView = binding.gidRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
            orientation = LinearLayout.VERTICAL,
            item = R.layout.skeleton_item_gid,
        )
    }

    private fun stopSkeleton() {
        stopSkeleton(
            recyclerView = binding.gidRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
        )
    }
}


