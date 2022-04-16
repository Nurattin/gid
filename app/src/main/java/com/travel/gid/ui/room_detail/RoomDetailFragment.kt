package com.travel.gid.ui.room_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.travel.gid.R
import com.travel.gid.data.models.Ameneti
import com.travel.gid.databinding.RoomDetailFragmentBinding
import com.travel.gid.ui.BaseFragment
import com.travel.gid.ui.hotel_detail.adapters.HotelAmenitiesAdapter
import com.travel.gid.ui.room_detail.adapters.RoomAdditionalAdapter
import com.travel.gid.ui.room_detail.adapters.RoomAllPricesAdapter
import com.travel.gid.ui.room_detail.adapters.RoomAmenitiesAdapter
import com.travel.gid.ui.room_detail.adapters.RoomDetailImagesAdapter

class RoomDetailFragment : BaseFragment<RoomDetailFragmentBinding>() {

    companion object {
        fun newInstance() = RoomDetailFragment()
    }

    private lateinit var viewModel: RoomDetailViewModel

    var adapterAdditional : RoomAdditionalAdapter = RoomAdditionalAdapter()
    var adapterAmenities : HotelAmenitiesAdapter? = null
    var allPricesAdapter = RoomAllPricesAdapter()
    var imgAdapter = RoomDetailImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomDetailFragmentBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.run {

            val amenitiesHotel = ArrayList<Ameneti>()
            amenitiesHotel.add(Ameneti("WIFI", R.drawable.ic_wifi))
            amenitiesHotel.add(Ameneti("Помощь малоподвижным", R.drawable.ic_help_people))
            amenitiesHotel.add(Ameneti("Бар", R.drawable.ic_bar))
            amenitiesHotel.add(Ameneti("Ресторан", R.drawable.ic_rest))
            amenitiesHotel.add(Ameneti("Спортзал", R.drawable.ic_gym))
            amenitiesHotel.add(Ameneti("Прачечная", R.drawable.ic_laundry))

            adapterAmenities =  HotelAmenitiesAdapter(amenitiesHotel)
            recyclerAmenities.adapter = adapterAmenities

            vpImages.adapter = imgAdapter
            vpImages.setPageTransformer(MarginPageTransformer(50))
            TabLayoutMediator(dotsIndicator, vpImages) { tab, position ->

            }.attach()

            recyclerAmenities.adapter = adapterAmenities
            recAdditionalOption.adapter = adapterAdditional
            includeViewPrice.run {
                allPrice.text = "5 707 ₽"
                recPrices.adapter = allPricesAdapter
            }
        }

    }


}