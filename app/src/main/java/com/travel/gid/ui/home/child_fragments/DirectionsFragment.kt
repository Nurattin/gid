package com.travel.gid.ui.home.child_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.travel.gid.R
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.FragmentVpHomeToursBinding
import com.travel.gid.ui.BaseFragment
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.home.adapters.DirectionsListAdapter
import com.travel.gid.ui.home.adapters.UpcomingToursAdapter
import com.travel.gid.utils.SpaceItemDecoration

class DirectionsFragment  : BaseFragment<FragmentVpHomeToursBinding>() {

    var directionList: ArrayList<DirectionData> = ArrayList()

    companion object {
        public fun newInstance(directionList: List<DirectionData>): DirectionsFragment {
            val fragment = DirectionsFragment()
            fragment.directionList = directionList.toCollection(ArrayList())

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVpHomeToursBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupUpcomingRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.run {

            similarToursRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = DirectionsListAdapter(array = directionList)
            similarToursRecyclerView.adapter = adapter

            adapter.setOnTourClickListener {
                navController.navigate(
                    R.id.directionDetailFragment,
                    DirectionDetailFragmentArgs(it.id,).toBundle()
                )
            }

            similarToursRecyclerView.clipToPadding = false

            similarToursRecyclerView.addItemDecoration(
                SpaceItemDecoration(
                    space = 50,
                    orientation = SpaceItemDecoration.Orientation.HORIZONTAL
                )
            )
        }
    }

    private fun setupUpcomingRecyclerView() {
        binding?.run {
            upcomingToursRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = UpcomingToursAdapter()
            upcomingToursRecyclerView.adapter = adapter
            upcomingToursRecyclerView.clipToPadding = false
        }
    }
}