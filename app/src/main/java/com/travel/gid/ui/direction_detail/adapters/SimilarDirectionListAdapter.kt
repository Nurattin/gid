package com.travel.gid.ui.direction_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travel.gid.R
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.ItemTourBinding

private typealias OnTourClickListener = ((DirectionData) -> Unit)

class SimilarDirectionListAdapter(private val array: ArrayList<DirectionData>) : RecyclerView.Adapter<SimilarDirectionListAdapter.TourViewHolder>() {

    private var clickListener: OnTourClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        return TourViewHolder(
            ItemTourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(array[position], clickListener)
    }

    override fun getItemCount(): Int {
        return array.size
    }


    class TourViewHolder(private val binding: ItemTourBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(directionModel: DirectionData, clickListener: OnTourClickListener?) {
            binding.run {
                cardTour.setOnClickListener {
                   // clickListener?.invoke(directionModel)
                }

                tvName.text = directionModel.name
                tvLocation.text = directionModel.city.name

                Glide.with(root)
                    .load(directionModel.avatar)
                    .placeholder(R.drawable.no_image)
                    .into(imgDirection)
            }
        }
    }

    fun setOnTourClickListener(listener: OnTourClickListener?) {
        clickListener = listener
    }

}
