package com.travel.gid.ui.home.adapters.banner_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.databinding.ItemBannerBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.SliderViewHolder>() {

    var data = listOf<String>()
        set(value) {
            field = value
            field = listOf(field.last()) + field + listOf(field.first())
            notifyDataSetChanged()
        }


    class SliderViewHolder(var binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.run {

                tourListImage2.setOnClickListener {
//                    clickListener.invoke()
                }
                Glide.with(root)
                    .load(item)
                    .placeholder(R.drawable.no_image)
                    .into(tourListImage2)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.SliderViewHolder {
        return ViewPagerAdapter.SliderViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}