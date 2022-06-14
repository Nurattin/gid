package com.travel.gid.ui.direction_tour.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.data.models.CategoriesData
import com.travel.gid.databinding.CategoriesItemBinding

internal typealias OnCategoriesDirectionClickListener = ((CategoriesData) -> Unit)


class DirectionCategoriesAdapter() : RecyclerView.Adapter<DirectionCategoriesAdapter.ViewHolder>() {

    private var clickListener: OnCategoriesDirectionClickListener? = null

    var positionCategories: Int = 0

    var data = listOf<CategoriesData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DirectionCategoriesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoriesItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DirectionCategoriesAdapter.ViewHolder, position: Int) =
        holder.bind(data[position], clickListener)

    override fun getItemCount(): Int = data.size

    inner class ViewHolder constructor(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoriesData, clickListener: OnCategoriesDirectionClickListener?) {
            binding.apply {
                categoryName.text = item.name
                backgroundChange(item)
                categoryName.setOnClickListener {

                    data[positionCategories].enable = false
                    data[adapterPosition].enable = true
                    notifyItemChanged(positionCategories)
                    notifyItemChanged(adapterPosition)
                    positionCategories = adapterPosition
                    clickListener?.invoke(item)
                }
            }
        }

        private fun CategoriesItemBinding.backgroundChange(item: CategoriesData) {
            if (!item.enable) {
                categoryName.setBackgroundResource(R.drawable.btn_categories_unenable)
                categoryName.setTextColor(Color.parseColor("#000000"))
            } else {
                categoryName.setBackgroundResource(R.drawable.btn_categories_enable)
                categoryName.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    fun setOnCategoriesDirectionClickListener(listener: OnCategoriesDirectionClickListener?) {
        clickListener = listener
    }

}

