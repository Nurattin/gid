package com.travel.gid.ui.select_city

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import com.travel.gid.databinding.ItemBannerBinding
import com.travel.gid.databinding.ItemChildGuestBinding
import com.travel.gid.databinding.ItemCityBinding

private typealias ChildCountClickListener = ((Int, Int) -> Unit)

class SelectCityAdapter(): RecyclerView.Adapter<SelectCityAdapter.CityViewHolder>() {

    private var clickListener: ChildCountClickListener? = null

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        holder.bind()
    }

    fun setOnTourClickListener(listener: ChildCountClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return count
    }

    class CityViewHolder(var binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(){
            binding.run {

            }
        }
    }
}