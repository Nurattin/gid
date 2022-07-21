package com.travel.gid.ui.tour_detail.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stfalcon.imageviewer.StfalconImageViewer
import com.travel.gid.R
import com.travel.gid.databinding.ViewPagerBannerTourDetailBinding


class ToursImageAdapter(private val sliderItems: List<String>) :
    RecyclerView.Adapter<ToursImageAdapter.TourDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourDetailViewHolder {
        return TourDetailViewHolder.fromLong(parent)
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class TourDetailViewHolder(private val binding: ViewPagerBannerTourDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                glide(item, imageBanner)
                imageBanner.setOnClickListener {
                    showImage(listOf(item), root.context, imageBanner)
                }
            }


        }

        fun glide(item: String, into: ImageView) {
            Glide.with(itemView.context)
                .load(item)
                .placeholder(R.drawable.no_image)
                .apply(
                    RequestOptions()
                        .error(R.drawable.no_image)
                )
                .into(into)
        }

        private fun showImage(images: List<String>, context: Context, imageBanner: ImageView) {
            StfalconImageViewer.Builder(context, images) { view, image ->
                glide(image, view)
            }.withHiddenStatusBar(false)
                .withTransitionFrom(imageBanner)
                .withBackgroundColor(context.getColor(R.color.white))
                .allowSwipeToDismiss(true)
                .show()
        }

        companion object {
            fun fromLong(parent: ViewGroup): TourDetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ViewPagerBannerTourDetailBinding.inflate(layoutInflater, parent, false)
                return TourDetailViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: TourDetailViewHolder, position: Int) {
        holder.bind(sliderItems[position])
    }
}