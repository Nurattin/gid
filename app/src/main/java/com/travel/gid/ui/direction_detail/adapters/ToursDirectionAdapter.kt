package com.travel.gid.ui.direction_detail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.travel.gid.R
import com.travel.gid.data.models.TourDirection
import com.travel.gid.databinding.ItemBannerBinding
import com.travel.gid.databinding.TourDirectionItemBinding
import com.travel.gid.ui.home.adapters.BannerListAdapter

private typealias OnDirectionDetailClickListener = ((Long) -> Unit)

class TourDirectionAdapter(var list: List<TourDirection>) : RecyclerView.Adapter<TourDirectionAdapter.TourDirectionViewHolder>() {

    private var clickListener: OnDirectionDetailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourDirectionViewHolder {
        return TourDirectionViewHolder(
            TourDirectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TourDirectionViewHolder,
        position: Int
    ) {
        holder.bind(list[position], clickListener)
    }

    class TourDirectionViewHolder(var binding: TourDirectionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TourDirection, clickListener: OnDirectionDetailClickListener?){
            binding.run {
                tourName.text = item.name
                txtDuration.text = item.duration
                txtCount.text = "${item.peopleCount} человек"
                txtPrice.text = "${item.price} ₽"

                button.setOnClickListener {
                    clickListener?.invoke(item.id)
                }
            }
        }
    }

    fun setOnTourClickListener(listener: OnDirectionDetailClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}