package com.travel.gid.ui.room_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomAmenitiesAdapter() :
    RecyclerView.Adapter<RoomAmenitiesAdapter.RoomViewHolder>() {
    var count = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ItemAmenetiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomViewHolder(var binding: ItemAmenetiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.run {

            }
        }
    }

}