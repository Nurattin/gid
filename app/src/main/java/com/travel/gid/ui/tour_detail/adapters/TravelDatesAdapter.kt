package com.travel.gid.ui.tour_detail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R

class TravelDatesAdapter : RecyclerView.Adapter<TravelDatesAdapter.TourDirectionViewHolder>() {

    private var position = 0

    class TourDirectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): TourDirectionViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                    .inflate(R.layout.travel_date_item, parent, false)
                return TourDirectionViewHolder(inflater)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourDirectionViewHolder {
        return TourDirectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: TravelDatesAdapter.TourDirectionViewHolder,
        position: Int
    ) {

        var travelDateCheckBox = holder.itemView.findViewById<CheckBox>(R.id.travel_date_check_box)

        travelDateCheckBox.isChecked = position == this.position

        holder.itemView.findViewById<ConstraintLayout>(R.id.travel_date).setOnClickListener {

            if(position != this.position) {
                val prevPos =  this.position
                this.position = position.toInt()
                notifyItemChanged(this.position)
                notifyItemChanged(prevPos)
            }

        }

    }

    override fun getItemCount(): Int {
        return 3
    }

}

