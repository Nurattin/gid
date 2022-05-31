package com.travel.gid.ui.home.adapters.eventAdapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.DirectionData

internal typealias OnEventClickListener = ((DirectionData) -> Unit)


class EventAdapter : RecyclerView.Adapter<EventRecyclerViewHolder>() {

    private var clickListener: OnEventClickListener? = null

    var data = listOf<DirectionData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRecyclerViewHolder {
        return if (viewType == 0)
            EventRecyclerViewHolder.LongViewHolder.fromLong(parent)
        else {
            EventRecyclerViewHolder.ShortViewHolder.fromShort(parent)
        }
    }

    override fun onBindViewHolder(holder: EventRecyclerViewHolder, position: Int) {
        when (holder) {
            is EventRecyclerViewHolder.LongViewHolder -> {
                holder.bind(data[position], clickListener)
            }
            is EventRecyclerViewHolder.ShortViewHolder -> {
                holder.bind(data[position], clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }

    override fun getItemCount(): Int = data.size

    fun setOnTourClickListener(listener: OnEventClickListener?) {
        clickListener = listener
    }
}