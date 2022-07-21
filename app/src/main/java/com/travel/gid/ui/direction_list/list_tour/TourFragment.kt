package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.FilterParamsTour
import com.travel.gid.databinding.FragmentTourBinding
import com.travel.gid.ui.base.BaseFragment
import com.travel.gid.ui.direction_list.list_tour.adapter.TourCategoriesAdapter
import com.travel.gid.ui.direction_list.list_tour.adapter.ToursAdapter
import com.travel.gid.ui.direction_list.list_tour.viewModel.TourViewModel
import com.travel.gid.ui.filter.FilterFragmentSheet
import com.travel.gid.ui.filter.FilterFragmentSheet.Companion.TAG
import com.travel.gid.ui.tour_detail.TourDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TourFragment : BaseFragment<FragmentTourBinding>() {

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
        val view = inflater.inflate(R.layout.fragment_tour, container, false)
        binding = FragmentTourBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainContent = binding.mainContent
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        refreshData = binding.refreshData
        errorInternetContainer = binding.errorInternetContainer
        recyclerView = binding.tourRecycler
        shimmer = binding.skeletonLayout.shimmerSkeleton
        itemLayoutSkeleton = R.layout.skeleton_item_tour_list

        showSkeleton()

        with(binding) {

            with(tourRecycler) {
                adapter = adapterTour
                viewModel.tours.observe(viewLifecycleOwner) { it ->
                    it.body()?.data?.let {
                        adapterTour.data = it
                    }
                    adapterTour.setOnTourClickListener {
                        navController.navigate(
                            R.id.tourDetailFragment,
                            TourDetailFragmentArgs(it.id).toBundle(),
                            defNavOption
                        )
                    }
                    stopSkeleton()
                }
            }

            with(categoriesRecycler) {
                adapter = adapterCategory
                viewModel.categories.observe(viewLifecycleOwner) {
                    adapterCategory.data = it
                    adapterCategory.positionCategories = viewModel.categoriesPos
                }
                adapterCategory.setOnCategoriesTourClickListener { categories, pos ->
                    getToursByCategories(pos, categories.id.toInt())
                }
            }

            viewModel.filters.observe(viewLifecycleOwner) {
                it.body()?.data.let { filterParams ->
                    if (viewModel.categories.value.isNullOrEmpty()) viewModel.getListCategories()
                    filter.fill(
                        filterParams!!.listCategories,
                        filterParams.price.priceFrom,
                        filterParams.price.priceTo
                    )
                    defaultFilterParams.fill(
                        filterParams.listCategories,
                        filterParams.price.priceFrom,
                        filterParams.price.priceTo
                    )
                    btnShowFilterBottomSheet.setOnClickListener {
                        if (!filterBottomSheetFragment.isAdded) {
                            filterBottomSheetFragment.filter = filter
                            filterBottomSheetFragment.show(parentFragmentManager, TAG)
                            filterBottomSheetFragment.setOnBtnApplyClickListener { filterParams ->
                                getToursByFilterParams(filterParams)
                                showSkeleton()
                            }
                        }
                    }
                }
            }

            viewModel.error.observe(viewLifecycleOwner) {
                showError()
            }

            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

            refresh.setOnClickListener {
                viewModel.getAllTour(filter)
                showSkeleton()
            }

            refreshData.setOnRefreshListener {
                viewModel.getAllTour(filter)
                showSkeleton()
            }


            range.setOnClickListener {
                showMenu(it, R.menu.popup_menu)
                animSort.playAnimation()
            }

            refresh.setOnClickListener {
                viewModel.refreshData(filter)
            }
        }
    }

    private fun getToursByFilterParams(filterParams: FilterParamsTour) {
        filter = filterParams
        filter.sortedParams = null
        viewModel.getAllTour(filterParams)
        adapterCategory.categoriesAllOn()
        viewModel.changeCategories(viewModel.categoriesPos, 0)
    }

    private fun showMenu(v: View?, @MenuRes popupMenu: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(popupMenu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_2 -> {
                    getSortedList("\"desc\"")
                }
                R.id.option_3 -> {
                    getSortedList("\"asc\"")
                }
            }
            binding.range.text = it.title.toString()
            true
        }
        popup.show()
    }

    private fun getSortedList(params: String) {
        if (filter.sortedParams != params) {
            filter.sortedParams = params
            viewModel.getAllTour(filter)
            showSkeleton()
        }
    }

    private fun getToursByCategories(pos: Int, categoriesId: Int) {
        with(viewModel) {
            setDefaultParams()
            changeCategories(viewModel.categoriesPos, pos)
            filter.categoriesAccept = if (categoriesId == 0) null else listOf(categoriesId)
            getAllTour(filter)
        }
        showSkeleton()
    }

    private fun setDefaultParams() {
        filter.fill(
            defaultFilterParams.categoriesName,
            defaultFilterParams.startPrice,
            defaultFilterParams.endPrice
        )
    }
}