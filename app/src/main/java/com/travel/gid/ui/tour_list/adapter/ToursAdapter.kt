package com.travel.gid.ui.tour_list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.TourData

internal typealias OnTourClickListener = ((TourData) -> Unit)

class ToursAdapter : RecyclerView.Adapter<TourRecyclerListViewHolder>() {

    private var clickListener: OnTourClickListener? = null

    var data = listOf<TourData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourRecyclerListViewHolder {
        return if (viewType == 0)
            TourRecyclerListViewHolder.LongListViewHolder.fromLong(parent)
        else {
            TourRecyclerListViewHolder.ShortListViewHolder.fromShort(parent)
        }
    }
    override fun onBindViewHolder(holder: TourRecyclerListViewHolder, position: Int) {
        when (holder) {
            is TourRecyclerListViewHolder.LongListViewHolder -> {
                holder.bind(data[position], clickListener)
            }
            is TourRecyclerListViewHolder.ShortListViewHolder -> {
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