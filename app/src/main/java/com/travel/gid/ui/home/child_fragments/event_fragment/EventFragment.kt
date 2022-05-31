package com.travel.gid.ui.home.child_fragments.event_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.travel.gid.R
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.FragmentEventBinding
import com.travel.gid.ui.home.adapters.eventAdapter.EventAdapter
import com.travel.gid.ui.home.child_fragments.DirectionsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventFragment : Fragment() {

    lateinit var binding: FragmentEventBinding

    private val viewModel: EventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        binding = FragmentEventBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val eventAdapter = EventAdapter()
        binding.eventRecycler.adapter = eventAdapter
        binding.eventRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel.event.observe(viewLifecycleOwner) { it ->
            it.body()?.data?.let {
                eventAdapter.data = it
            }
        }
    }
}