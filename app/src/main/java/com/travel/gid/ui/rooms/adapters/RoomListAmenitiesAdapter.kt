package com.travel.gid.ui.rooms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.Ameneti
import com.travel.gid.databinding.*


class RoomListAmenitiesAdapter(val listAmenities: ArrayList<Ameneti>) :
    RecyclerView.Adapter<RoomListAmenitiesAdapter.RoomListAmenitiesViewHolder>() {
    var count = listAmenities.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListAmenitiesViewHolder {
        return RoomListAmenitiesViewHolder(
            ItemRoomListAmenetiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomListAmenitiesViewHolder, position: Int) {
        holder.bind(listAmenities[position])
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomListAmenitiesViewHolder(var binding: ItemRoomListAmenetiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(amenity: Ameneti) {
            binding.run {
                textAmeneti.text = amenity.name
                imgAmeneti.setImageResource(amenity.img)
            }
        }
    }

}