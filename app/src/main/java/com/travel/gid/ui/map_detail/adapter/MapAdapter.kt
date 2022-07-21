package com.travel.gid.ui.map_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stfalcon.imageviewer.StfalconImageViewer
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.databinding.RouteDetailItemBinding


class MapAdapter() : RecyclerView.Adapter<MapAdapter.ViewHolder>() {

    var data = listOf<Places>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RouteDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(private val binding: RouteDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Places, position: Int) {
            binding.apply {
                placeName.text = item.name
                for (i in 0 until item.detailImages.size) {
                    if (i < 3) {
                        Glide.with(itemView.context)
                            .load(item.detailImages[i])
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.no_image)
                            )
                            .into(
                                when (i) {
                                    0 -> imageDetail1
                                    1 -> imageDetail2
                                    2 -> imageDetail3
                                    else -> imageDetail1
                                }
                            )
                    }
                }
                count.text = "${position + 1}"
                imageContainer.forEach {
                    if (it is CardView) it.setOnClickListener { v ->
                        val tag = v.tag.toString().toInt()
                        showImage(
                            item.detailImages,
                            binding.root.context,
                            tag,
                            it.getChildAt(0) as ImageView
                        )
                    }
                }
            }
        }
        private fun showImage(
            images: List<String>,
            context: Context,
            position: Int,
            imageView: View
        ) {

            StfalconImageViewer.Builder(context, images) { view, image ->
                Glide.with(itemView.context)
                    .load(image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.no_image)
                    ).into(
                        view
                    )
            }.withStartPosition(position - 1)
                .withHiddenStatusBar(false)
                .withTransitionFrom(imageView as ImageView)
                .withBackgroundColor(context.getColor(R.color.white))
                .allowSwipeToDismiss(true).show()
                .show()
        }
    }
}