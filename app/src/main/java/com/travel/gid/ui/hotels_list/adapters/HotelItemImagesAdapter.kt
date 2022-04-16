package com.travel.gid.ui.hotels_list.adapters

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import com.travel.gid.databinding.ItemBannerBinding
import com.travel.gid.databinding.ItemChildGuestBinding
import com.travel.gid.databinding.ItemCityBinding
import com.travel.gid.databinding.ItemHotelImgBinding


class HotelItemImagesAdapter(val images: ArrayList<Int>) :
    RecyclerView.Adapter<HotelItemImagesAdapter.HotelItemImagesViewHolder>() {
    var count = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelItemImagesViewHolder {
        return HotelItemImagesViewHolder(
            ItemHotelImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelItemImagesViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class HotelItemImagesViewHolder(var binding: ItemHotelImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(img: Int) {
            binding.run {
                imageHotel.setImageResource(img)
            }
        }
    }
}