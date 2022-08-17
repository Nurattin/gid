package com.travel.gid.ui.home.adapters.categories_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.data.models.CategoriesHome
import com.travel.gid.databinding.ItemCategoriesHomeBinding

internal typealias OnCategoriesClickListener = ((Int) -> Unit)


class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var clickListener: OnCategoriesClickListener? = null

    private val diffCallback = object : DiffUtil.ItemCallback<CategoriesHome>() {
        override fun areItemsTheSame(oldItem: CategoriesHome, newItem: CategoriesHome): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoriesHome, newItem: CategoriesHome): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun getItemCount() = differ.currentList.size

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
        holder.bind(differ.currentList[position])
    }


    inner class ViewHolder(var binding: ItemCategoriesHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoriesHome) {
            with(binding) {
                if (item.isChecked) {
                    categoriesIcon.setImageResource(item.iconChecked)
                    categoriesText.setTextColor(Color.parseColor("#000000"))
                    categoriesIcon.setBackgroundResource(R.drawable.bg_btns_home_menu_check)
                } else {
                    categoriesIcon.setImageResource(item.iconUnChecked)
                    categoriesText.setTextColor(Color.parseColor("#ACA6A6"))
                    categoriesIcon.setBackgroundResource(R.drawable.bg_btns_home_menu_uncheck)
                }
                categoriesText.text = item.name
                categoriesIcon.setOnClickListener {
                    clickListener?.invoke(absoluteAdapterPosition)
                }
            }
        }
    }

    fun setOnCategoriesClickListener(listener: OnCategoriesClickListener) {
        clickListener = listener
    }
}