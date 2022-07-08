package com.travel.gid.ui.home.child_fragments.gid_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.data.models.GidDetail
import com.travel.gid.databinding.GidItemBinding
import kotlin.random.Random


private typealias OnCallClickListener = (() -> Unit)

class GidAdapter() : RecyclerView.Adapter<GidAdapter.SliderViewHolder>() {

    var data = listOf<GidDetail>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var clickListener: OnCallClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            GidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setOnTourClickListener(listener: OnCallClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return 5
    }

    class SliderViewHolder(var binding: GidItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GidDetail) {


            with(binding) {
                Glide.with(itemView.context)
                    .load(item.image)
                    .placeholder(R.drawable.no_image)
                    .into(gidAvatar)
                gidName.text = item.name
                averageCheck.text = item.averageCheck.toString()
                rating.text = (0..10).random().toString()
            }


        }
    }
}