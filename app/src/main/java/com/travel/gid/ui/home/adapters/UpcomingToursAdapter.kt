package com.travel.gid.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.databinding.UpcomingToursItemBinding

class UpcomingToursAdapter () : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UpcomingViewHolder(
            UpcomingToursItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 4
    }


    inner class UpcomingViewHolder(private val binding: UpcomingToursItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

        }
    }
}