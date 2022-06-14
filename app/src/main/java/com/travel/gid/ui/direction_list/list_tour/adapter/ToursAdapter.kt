package com.travel.gid.ui.direction_list.list_tour.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.DirectionData
import com.travel.gid.data.models.TourData

internal typealias OnTourClickListener = ((TourData) -> Unit)

class ToursAdapter : RecyclerView.Adapter<TourRecyclerViewHolder>() {

    private var clickListener: OnTourClickListener? = null

    var data = listOf<TourData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourRecyclerViewHolder {
        return if (viewType == 0)
            TourRecyclerViewHolder.LongViewHolder.fromLong(parent)
        else {
            TourRecyclerViewHolder.ShortViewHolder.fromShort(parent)
        }
    }
    override fun onBindViewHolder(holder: TourRecyclerViewHolder, position: Int) {
        when (holder) {
            is TourRecyclerViewHolder.LongViewHolder -> {
                holder.bind(data[position], clickListener)
            }
            is TourRecyclerViewHolder.ShortViewHolder -> {
                holder.bind(data[position], clickListener)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }
    override fun getItemCount(): Int = data.size

    fun setOnTourClickListener(listener: OnTourClickListener?) {
        clickListener = listener
    }
}