package com.travel.gid.ui.direction_tour.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.DirectionData

internal typealias OnDirectionClickListener = ((DirectionData) -> Unit)

class DirectionListAdapter : RecyclerView.Adapter<DirectionRecyclerViewHolder>() {

    private var clickListener: OnDirectionClickListener? = null

    var data = listOf<DirectionData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionRecyclerViewHolder {
        return if (viewType == 0)
            DirectionRecyclerViewHolder.LongViewHolder.fromLong(parent)
        else {
            DirectionRecyclerViewHolder.ShortViewHolder.fromShort(parent)
        }
    }
    override fun onBindViewHolder(holder: DirectionRecyclerViewHolder, position: Int) {
        when (holder) {
            is DirectionRecyclerViewHolder.LongViewHolder -> {
                holder.bind(data[position], clickListener)
            }
            is DirectionRecyclerViewHolder.ShortViewHolder -> {
                holder.bind(data[position], clickListener)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }
    override fun getItemCount(): Int = data.size

    fun setOnDirectionClickListener(listener: OnDirectionClickListener?) {
        clickListener = listener
    }
}