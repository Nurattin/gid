package com.travel.gid.ui.direction_tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.databinding.FragmentDirectionListBinding
import com.travel.gid.ui.direction_tour.adapter.DirectionCategoriesAdapter
import com.travel.gid.ui.direction_tour.adapter.DirectionListAdapter
import com.travel.gid.ui.direction_tour.viewModel.DirectionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectionListFragment : Fragment() {

    val viewModel: DirectionListViewModel by viewModels()

    lateinit var binding: FragmentDirectionListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_direction_list, container, false)
        binding = FragmentDirectionListBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (viewModel.directionList == null) showProgress()

        val adapterDirection = DirectionListAdapter()
        binding.tourRecycler.adapter = adapterDirection
        viewModel.directionList.observe(viewLifecycleOwner) { it ->
            it.body()?.data?.let {
                adapterDirection.data = it
                stopProgress()
            }

        }

        val adapterCategory = DirectionCategoriesAdapter()
        binding.categoriesRecycler.adapter = adapterCategory
        viewModel.filters.observe(viewLifecycleOwner) {
            it.let {
                adapterCategory.data = it
                adapterCategory.positionCategories = viewModel.categoriesPos
            }
        }

        adapterCategory.setOnCategoriesDirectionClickListener {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            viewModel.changeCategories(viewModel.categoriesPos)
            viewModel.changePos(it.id.toInt())
        }

        binding.toolbar.setNavigationOnClickListener{findNavController().navigateUp()}

        super.onViewCreated(view, savedInstanceState)
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