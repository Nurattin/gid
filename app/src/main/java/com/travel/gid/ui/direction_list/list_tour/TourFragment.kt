package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.FilterParams
import com.travel.gid.databinding.FragmentTourBinding
import com.travel.gid.ui.direction_list.list_tour.adapter.TourCategoriesAdapter
import com.travel.gid.ui.direction_list.list_tour.adapter.ToursAdapter
import com.travel.gid.ui.direction_list.list_tour.viewModel.TourViewModel
import com.travel.gid.ui.filter.FilterFragmentSheet
import com.travel.gid.ui.filter.FilterFragmentSheet.Companion.TAG
import com.travel.gid.ui.tour_detail.TourDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.shimmerlayout.ShimmerLayout
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class TourFragment : Fragment() {

    private val viewModel: TourViewModel by viewModels()
    private var filterDetail = FilterParams()
    private val adapterTour = ToursAdapter()
    private val adapterCategory = TourCategoriesAdapter()
    private val filterSheet = FilterFragmentSheet()
    private lateinit var binding: FragmentTourBinding
    private lateinit var skeletonLayout: LinearLayout
    private lateinit var shimmer: ShimmerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tour, container, false)
        binding = FragmentTourBinding.bind(view)
        skeletonLayout = binding.skeletonLayout.skeletonLayout
        shimmer = binding.skeletonLayout.shimmerSkeleton
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgress()
        with(binding) {
            with(tourRecycler) {
                adapter = adapterTour
                viewModel.tours.observe(viewLifecycleOwner) { it ->
                    it.body()?.data?.let {
                        adapterTour.data = it
                    }
                    stopProgress()
                }
                adapterTour.setOnTourClickListener {
                    findNavController().navigate(
                        R.id.tourDetailFragment,
                        TourDetailFragmentArgs(it.id).toBundle(),
                        null
                    )
                }
            }

            with(categoriesRecycler) {
                adapter = adapterCategory
                viewModel.categories.observe(viewLifecycleOwner) {
                    it?.let {
                        adapterCategory.data = it
                        adapterCategory.positionCategories = viewModel.categoriesPos
                        visibility = View.VISIBLE
                    }
                }
                adapterCategory.setOnCategoriesTourClickListener { categories, pos ->
                    getToursByCategories(pos, categories.id.toInt())
                }
            }

            viewModel.error.observe(viewLifecycleOwner) {
                showRefresh()
            }

            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

            refresh.setOnClickListener {
                viewModel.getAllTour(filterDetail)
                showProgress()
            }

            refreshData.setOnRefreshListener {
                viewModel.getAllTour(filterDetail)
                showProgress()
            }

            selectFilter.setOnClickListener {
                if (!filterSheet.isAdded) {
                    filterSheet.filterDetail
                    filterSheet.show(parentFragmentManager, TAG)
                    filterSheet.setOnBtnApplyClickListener {
                        filterDetail = it
                        filterDetail.orderByPrice = null
                        viewModel.getAllTour(it)
                        showProgress()
                        adapterCategory.categoriesAllOn()
                        viewModel.changeCategories(viewModel.categoriesPos, 0)
                    }
                }
            }
            range.setOnClickListener {
                showMenu(it, R.menu.popup_menu)
                animSort.speed = 1F
                animSort.playAnimation()
            }
        }
    }

    private fun showMenu(v: View?, @MenuRes popupMenu: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(popupMenu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_2 -> {
                    getSortTours("\"desc\"")
                }
                R.id.option_3 -> {
                    getSortTours("\"asc\"")
                }
            }
            binding.range.text = it.title.toString()
            true
        }
        popup.show()
    }

    private fun getSortTours(params: String) {
        if (filterDetail.orderByPrice != params) {
            filterDetail.orderByPrice = params
            viewModel.getAllTour(filterDetail)
            showProgress()
        }
    }

    private fun stopProgress() {
        animateReplaceSkeleton(binding.tourRecycler)
        binding.refreshData.isRefreshing = false
    }

    private fun showProgress() {
        showSkeleton(true)
    }

    private fun showRefresh() {
        showSkeleton(true)
    }

    private fun getToursByCategories(pos: Int, categoriesId: Int) {
        with(viewModel) {
            filterSheet.filterDetail = FilterParams()
            changeCategories(viewModel.categoriesPos, pos)
            filterDetail = FilterParams()
            filterDetail.categories = if (categoriesId == 0) null else listOf(categoriesId)
            getAllTour(filterDetail)
        }
        showProgress()
    }

    private fun showSkeleton(show: Boolean) {
        if (show) {
            binding.tourRecycler.visibility = View.GONE
            if (skeletonLayout.isEmpty()) {
                for (i in 0..1) {
                    skeletonLayout.addView(
                        layoutInflater.inflate(
                            R.layout.skeleton_item_tour_list, null
                        )
                    )
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
        listView.visibility = View.VISIBLE
        showSkeleton(
            false,
        )
    }
}