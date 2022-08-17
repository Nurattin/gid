package com.travel.gid.ui.tour_list.adapter

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


sealed class TourRecyclerListViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class LongListViewHolder(private val binding: LongTourItemBinding) :
        TourRecyclerListViewHolder(binding) {
        fun bind(item: TourData, clickListener: OnTourClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }
                val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
                Glide.with(itemView.context)
                    .load(imgUri)
                    .apply(RequestOptions().error(R.drawable.no_image))
                    .into(imageTour)
                nameTour.text = item.name
                place.text = "г. Дагестан"
                favorites.isChecked = item.favorite

            }
        }

        companion object {
            fun fromLong(parent: ViewGroup): LongListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingLong = LongTourItemBinding.inflate(layoutInflater, parent, false)
                return LongListViewHolder(bindingLong)
            }
        }
    }

    class ShortListViewHolder(private val binding: ShortTourItemBinding) :
        TourRecyclerListViewHolder(binding) {
        fun bind(item: TourData, clickListener: OnTourClickListener?) {
            binding.run {
                cardTour.setOnClickListener { clickListener?.invoke(item) }
                val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
                Glide.with(itemView.context)
                    .load(imgUri)
                    .apply(RequestOptions().error(R.drawable.no_image))
                    .into(imageTour)
                nameTour.text = item.name
                place.text = "г. Дагестан"
                favorites.isChecked = item.favorite
            }
        }

        companion object {
            fun fromShort(parent: ViewGroup): ShortListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingShort = ShortTourItemBinding.inflate(layoutInflater, parent, false)
                return ShortListViewHolder(bindingShort)
            }
        }
    }
}
