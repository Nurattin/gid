package com.travel.gid.ui.home.child_fragments.event_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.size.Scale
import com.travel.gid.R
import com.travel.gid.data.models.Events
import com.travel.gid.databinding.LongEventItemBinding
import com.travel.gid.databinding.ShortEventItemBinding

sealed class EventRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    class LongViewHolder(private val binding: LongEventItemBinding) :
        EventRecyclerViewHolder(binding) {
        fun bind(item: Events.Event, clickListener: OnEventClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }

                imageTour.load(data = item.image) {
                    scale(Scale.FILL)
                    error(R.drawable.no_image)
                    placeholder(R.drawable.loading_animation)
                }
                nameTour.text = item.name
                place.text = item.city.name
            }
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
        fun bind(item: Events.Event, clickListener: OnEventClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                    clickListener?.invoke(item)
                }

                imageTour.load(data = item.image) {
                    scale(Scale.FILL)
                    error(R.drawable.no_image)
                    placeholder(R.drawable.loading_animation)
                }
                nameTour.text = item.name
                place.text = item.city.name
            }
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