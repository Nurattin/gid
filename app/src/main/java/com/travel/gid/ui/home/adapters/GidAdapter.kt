package com.travel.gid.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.GidItemBinding


private typealias OnCallClickListener = (() -> Unit)

class GidAdapter(): RecyclerView.Adapter<GidAdapter.SliderViewHolder>() {

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
        holder.bind()
    }

    fun setOnTourClickListener(listener: OnCallClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return 4
    }

     class SliderViewHolder(var binding: GidItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

        }

        fun bind(){

        }
    }
}