package com.travel.gid.ui.tour_detail.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.travel.gid.R
import com.travel.gid.databinding.ViewPagerBannerTourDetailBinding


class ToursImageAdapter(private val sliderItems: List<String>) :
    RecyclerView.Adapter<ToursImageAdapter.TourDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourDetailViewHolder {
        return TourDetailViewHolder.fromLong(parent)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class TourDetailViewHolder(private val binding: ViewPagerBannerTourDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {

            val imgUri = item.toUri().buildUpon().scheme("http").build()
            Log.i("dwa", "$imgUri")
            Glide.with(itemView.context)
                .load(imgUri)
                .placeholder(R.drawable.no_image)
                .apply(
                    RequestOptions()
                        .error(R.drawable.no_image)
                )
                .into(binding.imageBanner)
        }

        companion object {
            fun fromLong(parent: ViewGroup): TourDetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ViewPagerBannerTourDetailBinding.inflate(layoutInflater, parent, false)
                return TourDetailViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: TourDetailViewHolder, position: Int) {
        holder.bind(sliderItems[position])
    }
}