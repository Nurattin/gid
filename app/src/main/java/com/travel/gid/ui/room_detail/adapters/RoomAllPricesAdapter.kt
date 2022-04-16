package com.travel.gid.ui.room_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.*


class RoomAllPricesAdapter() :
    RecyclerView.Adapter<RoomAllPricesAdapter.RoomAllPricesViewHolder>() {
    var count = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAllPricesViewHolder {
        return RoomAllPricesViewHolder(
            ItemPriceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomAllPricesViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setOnTourClickListener() {

    }

    override fun getItemCount(): Int {
        return count
    }

    class RoomAllPricesViewHolder(var binding: ItemPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.run {
                when (position){
                    1 -> {
                        priceCategoryText.text = "Завтрак в номер"
                        priceText.text = "1 500 ₽"
                    }
                    2 -> {
                        priceCategoryText.text = "Бесплатная отмена"
                        priceText.text = "500 ₽"
                    }
                }
            }
        }
    }

}