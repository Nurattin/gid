package com.travel.gid.ui.home.adapters

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.data.models.TourData
import com.travel.gid.databinding.ItemBannerBinding

private typealias BannerClickListener = (() -> Unit)

class BannerListAdapter(private val sliderItems: List<String> ): RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {

    private var clickListener: BannerClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {

        holder.bind(sliderItems[position])
    }

    fun setOnTourClickListener(listener: BannerClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class BannerViewHolder(var binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String){
            binding.run {
                root.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        ViewCompat.setElevation(root, 1f)
                    } else {
                        // run scale animation and make it smaller
                        ViewCompat.setElevation(root, 0f)
                    }
                }

                tourListImage2.setOnClickListener {
//                    clickListener.invoke()
                }
                Glide.with(root)
                    .load(item)
                    .placeholder(R.drawable.no_image)
                    .into(tourListImage2)
            }
        }
    }
}