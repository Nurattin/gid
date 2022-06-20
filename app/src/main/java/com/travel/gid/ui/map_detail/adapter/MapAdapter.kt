package com.travel.gid.ui.map_detail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.databinding.RouteDetailItemBinding


class MapAdapter() : RecyclerView.Adapter<MapAdapter.ViewHolder>() {

    var data = listOf<Places>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RouteDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(private val binding: RouteDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Places, position: Int) {
            binding.apply {
                placeName.text = item.name
                for (i in 0 until item.detailImages.size) {
                    if (i < 3){
                    Glide.with(itemView.context)
                        .load(item.detailImages[i])
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.no_image)
                        )
                        .into(
                            when (i) {
                                0 -> imageDetail1
                                1 -> imageDetail2
                                2 -> imageDetail3
                                else -> imageDetail1
                            }
                        )
                    }
                }
                count.text = "${position + 1}"
            }

        }
    }
}