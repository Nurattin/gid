package com.travel.gid.ui.rooms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomImagesAdapter() :
    RecyclerView.Adapter<RoomImagesAdapter.RoomImagesViewHolder>() {
    var count = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomImagesViewHolder {
        return RoomImagesViewHolder(
            ItemRoomListImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomImagesViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomImagesViewHolder(var binding: ItemRoomListImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.run {

            }
        }
    }
}