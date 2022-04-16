package com.travel.gid.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.databinding.RoomsFragmentBinding
import com.travel.gid.ui.BaseFragment
import com.travel.gid.ui.hotel_detail.HotelDetailFragmentArgs
import com.travel.gid.ui.rooms.adapters.RoomsAdapter

class RoomsFragment : BaseFragment<RoomsFragmentBinding>() {

    companion object {
        fun newInstance() = RoomsFragment()
    }

    private val args by lazy { RoomsFragmentArgs.fromBundle(requireArguments()) }

    private lateinit var viewModel: RoomsViewModel

    lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomsAdapter = RoomsAdapter(args.roomsList)
        roomsAdapter.setOnRoomClickListener {
            findNavController().navigate(R.id.roomDetailFragment)
        }

        binding?.run {
            recRooms.adapter = roomsAdapter
        }

    }

}