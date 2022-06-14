package com.travel.gid.ui.direction_tour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.travel.gid.R
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.LongDirectionListItemBinding
import com.travel.gid.databinding.ShortDirectionListItemBinding

sealed class DirectionRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {


    class LongViewHolder(private val binding: LongDirectionListItemBinding) :
        DirectionRecyclerViewHolder(binding) {
        fun bind(item: DirectionData, clickListener: OnDirectionClickListener?) {
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
//                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.no_image)
                )
                .into(binding.imageTour)
            binding.nameTour.text = item.name
            binding.place.text = item.city.name
        }

        companion object {
            fun fromLong(parent: ViewGroup): LongViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingLong =
                    LongDirectionListItemBinding.inflate(layoutInflater, parent, false)
                return LongViewHolder(bindingLong)
            }
        }
    }

    class ShortViewHolder(private val binding: ShortDirectionListItemBinding) :
        DirectionRecyclerViewHolder(binding) {
        fun bind(item: DirectionData, clickListener: OnDirectionClickListener?) {
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

//                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.no_image)
                )
                .into(binding.imageTour)
            binding.nameTour.text = item.name
            binding.place.text = item.city.name
        }

        companion object {
            fun fromShort(parent: ViewGroup): ShortViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingShort =
                    ShortDirectionListItemBinding.inflate(layoutInflater, parent, false)
                return ShortViewHolder(bindingShort)
            }
        }
    }
}