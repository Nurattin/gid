package com.travel.gid.ui.hotel_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.Ameneti
import com.travel.gid.databinding.*


class HotelAmenitiesAdapter(val listAmenities: ArrayList<Ameneti>) :
    RecyclerView.Adapter<HotelAmenitiesAdapter.HotelDetailImagesViewHolder>() {
    var count = listAmenities.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelDetailImagesViewHolder {
        return HotelDetailImagesViewHolder(
            ItemAmenetiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelDetailImagesViewHolder, position: Int) {
        holder.bind(listAmenities[position])
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class HotelDetailImagesViewHolder(var binding: ItemAmenetiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(amenity: Ameneti) {
            binding.run {
                textAmeneti.text = amenity.name
                imgAmeneti.setImageResource(amenity.img)
            }
        }
    }

}