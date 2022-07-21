package com.travel.gid.ui.home.child_fragments.event_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.travel.gid.R
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.LongEventItemBinding
import com.travel.gid.databinding.ShortEventItemBinding

sealed class EventRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    class LongViewHolder(private val binding: LongEventItemBinding) :
        EventRecyclerViewHolder(binding) {
        fun bind(item: DirectionData, clickListener: OnEventClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
            val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
            Glide.with(itemView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()

                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                )
                .into(binding.imageTour)
            binding.nameTour.text = item.name
            binding.place.text = item.city.name
        }

        companion object {
            fun fromLong(parent: ViewGroup): LongViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingLong = LongEventItemBinding.inflate(layoutInflater, parent, false)
                return LongViewHolder(bindingLong)
            }
        }
    }

    class ShortViewHolder(private val binding: ShortEventItemBinding) :
        EventRecyclerViewHolder(binding) {
        fun bind(item: DirectionData, clickListener: OnEventClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
            val imgUri = item.avatar.toUri().buildUpon().scheme("http").build()
            Glide.with(itemView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()

                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                )
                .into(binding.imageTour)
            binding.nameTour.text = item.name
            binding.place.text = item.city.name
        }

        companion object {
            fun fromShort(parent: ViewGroup): ShortViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingShort = ShortEventItemBinding.inflate(layoutInflater, parent, false)
                return ShortViewHolder(bindingShort)
            }
        }
    }
}