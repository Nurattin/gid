package com.travel.gid.ui.hotels_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.travel.gid.R
import com.travel.gid.data.models.Ameneti
import com.travel.gid.data.models.Hotel
import com.travel.gid.data.models.Room
import com.travel.gid.databinding.HotelsFragmentBinding
import com.travel.gid.ui.base.BaseFragment
import com.travel.gid.ui.hotel_detail.HotelDetailFragmentArgs
import com.travel.gid.ui.hotels_list.adapters.HotelItemAdapter

class HotelsFragment : BaseFragment<HotelsFragmentBinding>() {

    companion object {
        fun newInstance() = HotelsFragment()
    }

    private lateinit var viewModel: HotelsViewModel
    private lateinit var adapterHotels: HotelItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HotelsFragmentBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotels = ArrayList<Hotel>()
        val aboutHotel = ArrayList<Ameneti>()
        aboutHotel.add(Ameneti("Парковка", R.drawable.ic_parking))
        aboutHotel.add(Ameneti("Ресепшн 24 часа", R.drawable.ic_clerk))
        aboutHotel.add(
            Ameneti(
                "Персонал говорит на языках: английский, немецкий",
                R.drawable.ic_speak_language
            )
        )

        val amenitiesHotel = ArrayList<Ameneti>()
        amenitiesHotel.add(Ameneti("WIFI", R.drawable.ic_wifi))
        amenitiesHotel.add(Ameneti("Помощь малоподвижным", R.drawable.ic_help_people))
        amenitiesHotel.add(Ameneti("Бар", R.drawable.ic_bar))
        amenitiesHotel.add(Ameneti("Ресторан", R.drawable.ic_rest))
        amenitiesHotel.add(Ameneti("Спортзал", R.drawable.ic_gym))
        amenitiesHotel.add(Ameneti("Можно с животными", R.drawable.ic_animal))
        amenitiesHotel.add(Ameneti("Прачечная", R.drawable.ic_laundry))

        val images = ArrayList<Int>()
        images.add(R.drawable.hotel1)
        images.add(R.drawable.hotel2)
        images.add(R.drawable.hotel3)
        hotels.add(Hotel("Lux", 3500, images, aboutHotel, amenitiesHotel, getRooms()))

        val images2 = ArrayList<Int>()
        images2.add(R.drawable.hotel2)
        images2.add(R.drawable.hotel1)
        images2.add(R.drawable.hotel3)
        hotels.add(Hotel("Вавилон", 4500, images2, aboutHotel, amenitiesHotel, getRooms()))

        val images3 = ArrayList<Int>()
        images3.add(R.drawable.hotel3)
        images3.add(R.drawable.hotel1)
        images3.add(R.drawable.hotel2)
        hotels.add(Hotel("Прибой", 6500, images3, aboutHotel, amenitiesHotel, getRooms()))

        adapterHotels = HotelItemAdapter(hotels)
        adapterHotels.setOnHotelClickListener { i, i2 ->
            findNavController().navigate(
                R.id.hotelDetailFragment,
                HotelDetailFragmentArgs(hotels[i]).toBundle()
            )
        }

        binding?.run {
            recHotels.adapter = adapterHotels
        }
    }

    fun getRooms(): ArrayList<Room>{
        val rooms = ArrayList<Room>()

        val amenitiesRoom1 = ArrayList<Ameneti>()
        amenitiesRoom1.add(Ameneti("Завтрак в номер", R.drawable.ic_breakfast))
        amenitiesRoom1.add(Ameneti("Бесплатная отмена", R.drawable.ic_card))

        val imagesRoom1 = ArrayList<Int>()
        imagesRoom1.add(R.drawable.room2)
        imagesRoom1.add(R.drawable.room3)
        imagesRoom1.add(R.drawable.room4)
        imagesRoom1.add(R.drawable.room5)
        imagesRoom1.add(R.drawable.room6)

        rooms.add(Room("Стандарт", 3500, amenitiesRoom1, imagesRoom1))

        val imagesRoom2 = ArrayList<Int>()
        imagesRoom2.add(R.drawable.room3)
        imagesRoom2.add(R.drawable.room2)
        imagesRoom2.add(R.drawable.room4)
        imagesRoom2.add(R.drawable.room5)
        imagesRoom2.add(R.drawable.room6)
        rooms.add(Room("Стандарт", 3500, amenitiesRoom1, imagesRoom2))

        val imagesRoom3 = ArrayList<Int>()
        imagesRoom3.add(R.drawable.room4)
        imagesRoom3.add(R.drawable.room3)
        imagesRoom3.add(R.drawable.room2)
        imagesRoom3.add(R.drawable.room5)
        imagesRoom3.add(R.drawable.room6)
        rooms.add(Room("Стандарт", 3500, amenitiesRoom1, imagesRoom3))

        val imagesRoom4 = ArrayList<Int>()
        imagesRoom3.add(R.drawable.room5)
        imagesRoom3.add(R.drawable.room4)
        imagesRoom3.add(R.drawable.room3)
        imagesRoom3.add(R.drawable.room2)
        imagesRoom3.add(R.drawable.room6)
        rooms.add(Room("Стандарт", 3500, amenitiesRoom1, imagesRoom4))

        return rooms
    }

}