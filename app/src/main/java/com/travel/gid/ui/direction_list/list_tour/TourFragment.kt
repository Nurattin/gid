package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
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

@AndroidEntryPoint
class TourFragment : Fragment() {

    private val viewModel: TourViewModel by viewModels()
    private var filterDetail = FilterParams()
    private val adapterTour = ToursAdapter()
    private val adapterCategory = TourCategoriesAdapter()
    private val filterSheet = FilterFragmentSheet()
    private lateinit var binding: FragmentTourBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tour, container, false)
        binding = FragmentTourBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (viewModel.tours.value == null) showProgress()
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
            }

            selectFilter.setOnClickListener {
                if (!filterSheet.isAdded) {
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
        binding.apply {
            progressBar.visibility = View.GONE
            tourRecycler.visibility = View.VISIBLE
            categoriesRecycler.visibility = View.VISIBLE
            refreshContainer.visibility = View.GONE
            binding.refreshData.isRefreshing = false
        }
    }

    private fun showProgress() {
        binding.apply {
            refreshContainer.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            tourRecycler.visibility = View.GONE
        }
    }

    private fun showRefresh() {
        with(binding) {
            progressBar.visibility = View.GONE
            refreshContainer.visibility = View.VISIBLE
        }
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
}