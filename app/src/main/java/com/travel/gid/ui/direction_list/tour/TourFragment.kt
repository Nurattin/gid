package com.travel.gid.ui.direction_list.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.travel.gid.R
import com.travel.gid.databinding.FragmentTourBinding
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.direction_list.tour.adapter.CategoriesAdapter
import com.travel.gid.ui.direction_list.tour.adapter.ToursAdapter
import com.travel.gid.ui.direction_list.tour.viewModel.TourViewModel
import com.travel.gid.ui.home.HomeViewModel
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tour, container, false)
        binding = FragmentTourBinding.bind(view)



        val tourCategory = ToursAdapter()
        binding.tourRecycler.adapter = tourCategory
        viewModel.tours.observe(viewLifecycleOwner,){ it ->
            it.body()?.data?.let {
                tourCategory.data = it
            }
        }



        val adapterCategory = CategoriesAdapter()
        binding.categoriesRecycler.adapter = adapterCategory
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            adapterCategory.data = it
        })


        tourCategory.setOnTourClickListener {
            findNavController().navigate(
                R.id.tourDetailFragment,
                TourDetailFragmentArgs(it.id).toBundle(),
                null
            )
        }

        binding.tourRecycler.setHasFixedSize(true)

        binding.toolbar.setNavigationOnClickListener{findNavController().navigateUp()}
        
        return binding.root
    }
}