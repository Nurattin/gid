package com.travel.gid.ui.home.child_fragments.gid_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.travel.gid.R
import com.travel.gid.data.models.GidDetail
import com.travel.gid.databinding.GidItemBinding


private typealias OnCallClickListener = (() -> Unit)

class GidAdapter : RecyclerView.Adapter<GidAdapter.SliderViewHolder>() {

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
        return data.size
    }

    class SliderViewHolder(var binding: GidItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GidDetail) {


            with(binding) {

                gidAvatar.load(data = item.image) {
                    scale(Scale.FILL)
                    error(R.drawable.no_image)
                    placeholder(R.drawable.loading_animation)
                }

                gidName.text = item.name
                averageCheck.text = item.averageCheck.toString()
                rating.text = (0..10).random().toString()
            }


        }
    }
}