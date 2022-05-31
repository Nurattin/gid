package com.travel.gid.ui.direction_list.tour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.data.models.Categories
import com.travel.gid.databinding.CategoriesItemBinding

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var data = listOf<Categories>()
        set(value) { field = value
            notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder private constructor(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Categories) {binding.categoryName.text = item.name}

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoriesItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

