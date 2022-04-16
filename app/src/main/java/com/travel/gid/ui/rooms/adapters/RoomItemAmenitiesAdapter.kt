package com.travel.gid.ui.rooms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomItemAmenitiesAdapter() :
    RecyclerView.Adapter<RoomItemAmenitiesAdapter.RoomViewHolder>() {
    var count = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ItemRoomListAmenetiesBinding.inflate(
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

    class RoomViewHolder(var binding: ItemRoomListAmenetiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.run {

            }
        }
    }

}