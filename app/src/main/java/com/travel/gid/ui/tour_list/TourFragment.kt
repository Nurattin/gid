package com.travel.gid.ui.tour_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.FilterParamsTour
import com.travel.gid.data.models.Tour
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.databinding.FragmentTourBinding
import com.travel.gid.ui.delegation.Error
import com.travel.gid.ui.delegation.ErrorImpl
import com.travel.gid.ui.delegation.Skeleton
import com.travel.gid.ui.delegation.SkeletonImpl
import com.travel.gid.ui.filter.FilterFragmentSheet
import com.travel.gid.ui.tour_detail.TourDetailFragmentArgs
import com.travel.gid.ui.tour_list.adapter.TourCategoriesAdapter
import com.travel.gid.ui.tour_list.adapter.ToursAdapter
import com.travel.gid.ui.tour_list.compose.FilterBtn
import com.travel.gid.ui.tour_list.compose.RangeBtn
import com.travel.gid.ui.tour_list.viewModel.TourViewModel
import com.travel.gid.utils.ConnectionLiveData
import com.travel.gid.utils.checkInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TourFragment : Fragment(), Error by ErrorImpl(), Skeleton by SkeletonImpl() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData
    lateinit var binding: FragmentTourBinding
    private val viewModel: TourViewModel by viewModels()
    private val defaultFilterParams = FilterParamsTour()
    private var filter = FilterParamsTour()
    private val adapterTour = ToursAdapter()
    private val adapterCategory = TourCategoriesAdapter()
    private val filterBottomSheetFragment = FilterFragmentSheet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourBinding.inflate(inflater, container, false)
        binding.btnShowFilterBottomSheet.setContent {
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FilterBtn {
                    showBottomShitFilterFragment()
                }
                RangeBtn(
                    startPos = viewModel.startPost,
                    listSortOptions = listOf(
                        R.string.popularityFirst,
                        R.string.dearFirst,
                        R.string.cheapFirst,
                        R.string.newFirst,
                    )
                ) { index, item ->
                    viewModel.startPost = index
                    getSortedList(item)
                }
            }
        }

        binding.categoriesRecycler.adapter = adapterCategory
        binding.tourRecycler.adapter = adapterTour
        return binding.root
    }

    private fun showBottomShitFilterFragment() {

        filterBottomSheetFragment.setOnBtnApplyClickListener { filterParams ->
            getToursByFilterParams(filterParams)
            showSkeletonForTourList()
        }
        findNavController().navigate(R.id.filterFragmentSheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        networkConnection.observe(viewLifecycleOwner) {
            when (it) {
                true -> getDataForListTour()
                false -> {}
            }
        }
        viewModel.tours.observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Success -> showListTourList(result.asSuccess().value)

                is Result.Failure<*> -> {
                    result.asFailure().error.let { throwable ->
                        viewModel.swipeProgress.value = false
                        showError(
                            mainContainer = binding.mainContent,
                            errorContainer = binding.errorInternetContainer,
                            throwable = throwable ?: Throwable("Unknown error"),
                            actionOnErrorButton = { getDataForListTour() }
                        )
                    }
                }
            }
            stopSkeletonTourList()
        }


        viewModel.filters.observe(viewLifecycleOwner) { result ->
            when (result) {

                is Result.Success -> {
                    result.asSuccess().value.data.let { filterParams ->
                        if (viewModel.categories.value.isNullOrEmpty()) viewModel.getListCategories()
                        filter.fill(
                            filterParams.listCategories,
                            filterParams.price.priceFrom,
                            filterParams.price.priceTo
                        )
                        defaultFilterParams.fill(
                            filterParams.listCategories,
                            filterParams.price.priceFrom,
                            filterParams.price.priceTo
                        )
                    }
                }

                is Result.Failure<*> -> {

                }
            }
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            adapterCategory.data = it
            adapterCategory.positionCategories = viewModel.categoriesPos
            adapterCategory.setOnCategoriesTourClickListener { categories, pos ->
                getToursByCategories(pos, categories.id.toInt())
            }
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.refreshData.setOnRefreshListener {
            getDataForListTour()
            showSkeletonForTourList()
        }
    }

    private fun showListTourList(value: Tour) {
        value.data.let { listTour ->
            adapterTour.data = listTour
            adapterTour.setOnTourClickListener {
                findNavController().navigate(
                    R.id.tourDetailFragment,
                    TourDetailFragmentArgs(it.id).toBundle(),
                    null
                )
            }
            binding.refreshData.isRefreshing = false
            stopSkeletonTourList()
        }
    }

    private fun getDataForListTour() {
        networkConnection.checkInternetConnection(
            actionOnInternetConnection = {
                if (binding.errorInternetContainer.isVisible) hideError()
                showSkeletonForTourList()
                viewModel.getAllTour(filter)
            },
            actionOnInternetDisconnection = {
                binding.refreshData.isRefreshing = false
                if (!binding.errorInternetContainer.isVisible) {
                    showError(
                        mainContainer = binding.mainContent,
                        errorContainer = binding.errorInternetContainer,
                        actionOnErrorButton = { getDataForListTour() }
                    )
                }
            }
        )

    }

    private fun getToursByFilterParams(filterParams: FilterParamsTour) {
        filter = filterParams
        filter.sortedParams = null
        viewModel.getAllTour(filterParams)
        adapterCategory.categoriesAllOn()
        viewModel.changeCategories(viewModel.categoriesPos, 0)
    }

    private fun getSortedList(item: Int) {

        val params: String? = when (item) {
            R.string.cheapFirst -> "\"asc\""
            R.string.dearFirst -> "\"desc\""
            R.string.popularityFirst -> {
                null
            }
            R.string.newFirst -> {
                null
            }
            else -> {
                null
            }
        }
        if (filter.sortedParams != params) {
            filter.sortedParams = params
            viewModel.getAllTour(filter)
            showSkeletonForTourList()
        }
    }

    private fun getToursByCategories(pos: Int, categoriesId: Int) {
        with(viewModel) {
            setDefaultParams()
            changeCategories(viewModel.categoriesPos, pos)
            filter.categoriesAccept = if (categoriesId == 0) null else listOf(categoriesId)
            getAllTour(filter)
        }
        showSkeletonForTourList()
    }

    private fun setDefaultParams() {
        filter.fill(
            defaultFilterParams.categoriesName,
            defaultFilterParams.startPrice,
            defaultFilterParams.endPrice
        )
    }

    private fun showSkeletonForTourList() {
        showSkeleton(
            context = requireContext(),
            recyclerView = binding.tourRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
            orientation = LinearLayout.VERTICAL,
            item = R.layout.skeleton_item_tour_list
        )
    }

    private fun stopSkeletonTourList() {
        stopSkeleton(
            recyclerView = binding.tourRecycler,
            shimmerLayout = binding.skeletonLayout.shimmerSkeleton,
        )
    }

    private fun hideError() {
        hideError(
            mainContainer = binding.mainContent,
            errorContainer = binding.errorInternetContainer
        )
        binding.refreshData.isRefreshing = false
    }
}