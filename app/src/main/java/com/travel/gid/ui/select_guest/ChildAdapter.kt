package com.travel.gid.ui.select_guest

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import com.travel.gid.databinding.ItemBannerBinding
import com.travel.gid.databinding.ItemChildGuestBinding

private typealias ChildCountClickListener = ((Int, Int) -> Unit)

class ChildAdapter(): RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    private var clickListener: ChildCountClickListener? = null

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(
            ItemChildGuestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        holder.bind()
    }

    fun setOnTourClickListener(listener: ChildCountClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return count
    }

    class ChildViewHolder(var binding: ItemChildGuestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(){
            binding.run {

            }
        }
    }
}