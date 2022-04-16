package com.travel.gid.ui.room_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomAdditionalAdapter() :
    RecyclerView.Adapter<RoomAdditionalAdapter.RoomAdditionalViewHolder>() {
    var count = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdditionalViewHolder {
        return RoomAdditionalViewHolder(
            ItemAdditionalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomAdditionalViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomAdditionalViewHolder(var binding: ItemAdditionalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.run {

                root.setOnClickListener {
                    checkbox.isChecked = !checkbox.isChecked
                }
                when (position){
                    0 -> {
                        additionalName.text = "Завтрак в номер"
                        price.text = "1 500 ₽"
                    }
                }

            }
        }
    }
}