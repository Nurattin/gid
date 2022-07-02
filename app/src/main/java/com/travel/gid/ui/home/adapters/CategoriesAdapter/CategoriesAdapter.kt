package com.travel.gid.ui.home.adapters.CategoriesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.data.models.CategoriesHome
import com.travel.gid.databinding.ItemCategoriesHomeBinding

internal typealias OnCategoriesClickListener = ((Int) -> Unit)

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    var data = listOf<CategoriesHome>()
        set(value) {
            field = value
            field[0].isChecked = true
            notifyDataSetChanged()
        }

    var clickListener: OnCategoriesClickListener? = null
    var lastPosition = 0

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoriesHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class ViewHolder(var binding: ItemCategoriesHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoriesHome) {
            with(binding) {
                if (item.isChecked) {
                    categoriesIcon.setImageResource(item.iconChecked)
                    categoriesText.setTextColor(R.color.black)
                    categoriesIcon.setBackgroundResource(R.drawable.bg_btns_home_menu_check)
                } else {
                    categoriesIcon.setImageResource(item.iconUnChecked)
                    categoriesText.setTextColor(R.color.white_40_per)
                    categoriesIcon.setBackgroundResource(R.drawable.bg_btns_home_menu_uncheck)
                }
                categoriesText.text = item.name
                categoriesIcon.setOnClickListener {
                    if (lastPosition != adapterPosition) {
                        data[adapterPosition].isChecked = true
                        notifyItemChanged(adapterPosition)
                        data[lastPosition].isChecked = false
                        notifyItemChanged(lastPosition)
                        lastPosition = adapterPosition
                        clickListener?.invoke(adapterPosition)
                    }
                }
            }
        }
    }
    fun setOnCategoriesClickListener(listener: OnCategoriesClickListener) {
        clickListener = listener
    }
}