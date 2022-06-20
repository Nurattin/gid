package com.travel.gid.ui.direction_list.list_tour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.travel.gid.R
import com.travel.gid.data.models.TourData
import com.travel.gid.databinding.LongTourItemBinding
import com.travel.gid.databinding.ShortTourItemBinding

sealed class TourRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    class LongViewHolder(private val binding: LongTourItemBinding) :
        TourRecyclerViewHolder(binding) {
        fun bind(item: TourData, clickListener: OnTourClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
            val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
            Glide.with(itemView.context)
                .load(imgUri)
                .apply(RequestOptions().error(R.drawable.no_image))
                .into(binding.imageTour)
            binding.nameTour.text = item.name
        }

        companion object {
            fun fromLong(parent: ViewGroup): LongViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingLong = LongTourItemBinding.inflate(layoutInflater, parent, false)
                return LongViewHolder(bindingLong)
            }
        }
    }

    class ShortViewHolder(private val binding: ShortTourItemBinding) :
        TourRecyclerViewHolder(binding) {
        fun bind(item: TourData, clickListener: OnTourClickListener?) {
            binding.run {
                cardTour.setOnClickListener{clickListener?.invoke(item)}
            }
            val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
            Glide.with(itemView.context)
                .load(imgUri)
                .apply(RequestOptions().error(R.drawable.no_image))
                .into(binding.imageTour)
            binding.nameTour.text = item.name
            binding.place.text = "г. Дагестан"
        }

        companion object {
            fun fromShort(parent: ViewGroup): ShortViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingShort = ShortTourItemBinding.inflate(layoutInflater, parent, false)
                return ShortViewHolder(bindingShort)
            }
        }
    }
}