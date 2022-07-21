package com.travel.gid.ui.direction_list.list_tour.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.data.models.Categories
import com.travel.gid.databinding.CategoriesItemBinding

internal typealias OnCategoriesTourClickListener = ((Categories, Int) -> Unit)

class TourCategoriesAdapter : RecyclerView.Adapter<TourCategoriesAdapter.ViewHolder>() {

    private var clickListener: OnCategoriesTourClickListener? = null
    var positionCategories: Int = 0

    var data = listOf<Categories>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoriesItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], clickListener)

    override fun getItemCount(): Int = data.size

    inner class ViewHolder constructor(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Categories, clickListener: OnCategoriesTourClickListener?) {
            binding.apply {
                categoryName.text = item.name

                categoryName.setOnClickListener {
                    if (adapterPosition != positionCategories) {
                        data[positionCategories].enable = false
                        data[adapterPosition].enable = true
                        notifyItemChanged(adapterPosition)
                        notifyItemChanged(positionCategories)
                        positionCategories = adapterPosition
                        clickListener?.invoke(item, adapterPosition)
                    }
                }
                backgroundChange(item)
            }
        }

        private fun CategoriesItemBinding.backgroundChange(item: Categories) {
            if (!item.enable) {
                categoryName.setBackgroundResource(R.drawable.btn_categories_unenable)
                categoryName.setTextColor(Color.parseColor("#000000"))
            } else {
                categoryName.setBackgroundResource(R.drawable.btn_categories_enable)
                categoryName.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    fun categoriesAllOn() {

        if (!data[0].enable) {
            data[0].enable = true
            notifyItemChanged(0)
            notifyItemChanged(positionCategories)
            positionCategories = 0
        }

    }

    fun setOnCategoriesTourClickListener(listener: OnCategoriesTourClickListener?) {
        clickListener = listener
    }
}

