package com.travel.gid.ui.room_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomSimilarAdapter() :
    RecyclerView.Adapter<RoomSimilarAdapter.RoomSimilarViewHolder>() {
    var count = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomSimilarViewHolder {
        return RoomSimilarViewHolder(
            ItemAmenetiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomSimilarViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomSimilarViewHolder(var binding: ItemAmenetiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.run {

            }
        }
    }

}