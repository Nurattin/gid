package com.travel.gid.ui.rooms.adapters

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.travel.gid.data.models.Room
import com.travel.gid.databinding.*
import com.travel.gid.ui.hotel_detail.adapters.HotelAmenitiesAdapter
import com.travel.gid.ui.hotels_list.adapters.HotelItemImagesAdapter


private typealias RoomClickListener = ((Int) -> Unit)

class RoomsAdapter(val listRoom: Array<Room>) :
    RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {
    var count = listRoom.count()

    var clickListener: RoomClickListener? = null

    var adapterAmenities: RoomListAmenitiesAdapter? = null
    var imgAdapter: HotelItemImagesAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        return RoomsViewHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(listRoom[position], clickListener)
    }

    fun setOnRoomClickListener(clickListener: RoomClickListener) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int {
        return count
    }

    inner class RoomsViewHolder(var binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Room, clickListener: RoomClickListener?) {

            adapterAmenities = RoomListAmenitiesAdapter(item.ameneties)
            imgAdapter = HotelItemImagesAdapter(item.images)

            binding.run {
                recyclerAmenities.adapter = adapterAmenities
                vpImages.adapter = imgAdapter
                vpImages.setPageTransformer(MarginPageTransformer(50))
                TabLayoutMediator(dotsIndicator, vpImages) { tab, position ->

                }.attach()

                detailRoomBtn.setOnClickListener {
                    clickListener?.invoke(1)
                }
            }
        }
    }

}