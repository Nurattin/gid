package com.travel.gid.ui.room_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomDetailImagesAdapter() :
    RecyclerView.Adapter<RoomDetailImagesAdapter.RoomDetailImagesViewHolder>() {
    var count = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomDetailImagesViewHolder {
        return RoomDetailImagesViewHolder(
            ItemRoomListImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomDetailImagesViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomDetailImagesViewHolder(var binding: ItemRoomListImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.run {

            }
        }
    }
}