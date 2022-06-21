package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.databinding.FragmentTourBinding
import com.travel.gid.ui.direction_list.list_tour.FilterFragmentSheet.Companion.TAG
import com.travel.gid.ui.direction_list.list_tour.adapter.TourCategoriesAdapter
import com.travel.gid.ui.direction_list.list_tour.adapter.ToursAdapter
import com.travel.gid.ui.direction_list.list_tour.viewModel.TourViewModel
import com.travel.gid.ui.tour_detail.TourDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TourFragment : Fragment() {

    private val viewModel: TourViewModel by viewModels()
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

        if (viewModel.tours == null) showProgress()

        val adapterTour = ToursAdapter()
        binding.tourRecycler.adapter = adapterTour
        viewModel.tours.observe(viewLifecycleOwner) { it ->
            it.body()?.data?.let {
                adapterTour.data = it
            }
            stopProgress()
        }


        val adapterCategory = TourCategoriesAdapter()
        binding.categoriesRecycler.adapter = adapterCategory
        viewModel.categories.observe(viewLifecycleOwner) {
            it?.let {
                adapterCategory.data = it
                adapterCategory.positionCategories = viewModel.categoriesPos
            }
        }


        adapterTour.setOnTourClickListener {
            findNavController().navigate(
                R.id.tourDetailFragment,
                TourDetailFragmentArgs(it.id).toBundle(),
                null
            )
        }

        adapterCategory.setOnCategoriesTourClickListener { categories, pos ->
            if (viewModel.categoriesPos != pos) {
                viewModel.changeCategories(viewModel.categoriesPos)
                viewModel.changePos(pos)
                when (categories.id) {
                    0L -> viewModel.getAllTour(null)
                    else -> viewModel.getTourByCategories(listOf(categories.id.toInt()))
                }
                showProgress()
            }
        }

        binding.tourRecycler.setHasFixedSize(true)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.apply {
            selectFilter.setOnClickListener {
                val filterSheet = FilterFragmentSheet()
                if (!filterSheet.isAdded) {
                    filterSheet.show(parentFragmentManager, TAG)
                    filterSheet.setOnBtnApplyClickListener {
                        Log.i("dwa", "$it")
                        viewModel.getAllTour(it)
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
                R.id.option_1 -> binding.range.text = "По популятрности"
                R.id.option_2 -> binding.range.text = "Сначала дорогие"
                R.id.option_3 -> binding.range.text = "Сначала дешевые"
                R.id.option_4 -> binding.range.text = "Новые"
            }
            true
        }
        popup.show()

    }

    private fun stopProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            tourRecycler.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tourRecycler.visibility = View.GONE
        }
    }
}