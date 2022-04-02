package com.travel.gid.ui.direction_detail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.data.models.TourData

class DirectionImageAdapter (private val sliderItems: List<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    class SliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pager_banner_tour_detail, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val url = "http://admin.gidtravel.xyz/storage/"
        Glide.with(holder.itemView.context)
            .load(url + sliderItems[position])
            .placeholder(R.drawable.no_image)
            .into(holder.itemView.findViewById(R.id.imageBanner))
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }
}