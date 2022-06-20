package com.travel.gid.ui.hotel_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.Place
import com.travel.gid.data.models.Room
import com.travel.gid.databinding.HotelDetailFragmentBinding
import com.travel.gid.ui.BaseFragment
import com.travel.gid.ui.hotel_detail.adapters.HotelAmenitiesAdapter
import com.travel.gid.ui.hotels_list.adapters.HotelItemImagesAdapter
import com.travel.gid.ui.rooms.RoomsFragmentArgs
import com.travel.gid.utils.CustomPointer
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.ui_view.ViewProvider

class HotelDetailFragment : BaseFragment<HotelDetailFragmentBinding>() {

    companion object {
        fun newInstance() = HotelDetailFragment()
    }

    private val args by lazy { HotelDetailFragmentArgs.fromBundle(requireArguments()) }

    private lateinit var viewModel: HotelDetailViewModel
    lateinit var adapterImages: HotelItemImagesAdapter
    lateinit var adapterAmenities: HotelAmenitiesAdapter
    lateinit var adapterAboutHotel: HotelAmenitiesAdapter

//    private val arrayPlaces =
//        arrayListOf(
//            Place(title = "Махачкала", coordinate = Point(42.96663, 47.51263))
//        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HotelDetailFragmentBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotel =  args.hotel

        adapterImages = HotelItemImagesAdapter(hotel.images)
        adapterAmenities = HotelAmenitiesAdapter(hotel.amenetiesHotel)
        adapterAboutHotel = HotelAmenitiesAdapter(hotel.aboutHotel)

//        initMarkPlaces()
//        setupMap()

        binding?.run {
            stars.rating = 4.0f
            hotelName.text = hotel.name
            priceRoom.text = "${hotel.price} ₽"
            vpImages.adapter = adapterImages
            recAboutHotel.adapter = adapterAboutHotel
            recAmenitiesRoom.adapter = adapterAmenities
        }

        binding?.allRoom?.setOnClickListener {
            findNavController().navigate(R.id.roomsFragment, RoomsFragmentArgs(args.hotel.rooms.toTypedArray()).toBundle())
        }
    }

//    private fun setupMap() {
//        binding?.run {
//            mapview.map.isRotateGesturesEnabled = false
//            val boundingBox = BoundingBox(
//                Point(42.96663, 47.51263),
//                Point(42.96663, 47.51263)
//            )
//            var cameraPosition = mapview.map.cameraPosition(boundingBox)
//            cameraPosition = CameraPosition(
//                cameraPosition.target,
//                cameraPosition.zoom - 4f,
//                cameraPosition.azimuth,
//                cameraPosition.tilt
//            )
//            mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 0f), null)
//            mapview.setNoninteractive(true)
//        }
//    }
//
//    private fun initMarkPlaces() {
//        binding?.run {
//            val mapObjects = mapview.map.mapObjects.addCollection()
//
//            arrayPlaces.forEachIndexed { index, place ->
//                val pointer = CustomPointer(requireContext())
//
//                pointer.setValues(place = index + 1, false)
//
//                val viewProvider = ViewProvider(pointer)
//                mapObjects.addPlacemark(place.coordinate, viewProvider)
//            }
//        }

//    }

    override fun onStop() {
        super.onStop()

        binding.run {
            binding?.run {
                mapview.onStop()
            }
        }
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()

        binding.run {
            binding?.run {
                mapview.onStart()
            }
        }

        MapKitFactory.getInstance().onStart()
    }


}
