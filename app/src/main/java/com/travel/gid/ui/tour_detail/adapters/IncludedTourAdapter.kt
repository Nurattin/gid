package com.travel.gid.ui.tour_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.databinding.ItemIncludedTourBinding



class IncludedTour(
    var image: Int,
    var name: String
)

class IncludedTourAdapter(var list: List<IncludedTour>): RecyclerView.Adapter<IncludedTourAdapter.SliderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            ItemIncludedTourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int {
        return list.count()
    }

     class SliderViewHolder(var binding: ItemIncludedTourBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IncludedTour){
            binding.run {
                name.text = item.name

                Glide.with(root)
                    .load(item.image)
                    .placeholder(R.drawable.no_image)
                    .into(img)
            }
        }
    }
}