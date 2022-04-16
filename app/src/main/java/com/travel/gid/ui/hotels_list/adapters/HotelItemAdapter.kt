package com.travel.gid.ui.hotels_list.adapters

import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.travel.gid.data.models.Hotel
import com.travel.gid.databinding.ItemBannerBinding
import com.travel.gid.databinding.ItemChildGuestBinding
import com.travel.gid.databinding.ItemCityBinding
import com.travel.gid.databinding.ItemHotelBinding

private typealias HotelClickListener = ((Int, Int) -> Unit)

class HotelItemAdapter(val arrayList: ArrayList<Hotel>): RecyclerView.Adapter<HotelItemAdapter.HotelItemViewHolder>() {

    private var clickListener: HotelClickListener? = null

    private var adapter: HotelItemImagesAdapter? = null

    var count = arrayList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelItemViewHolder {
        return HotelItemViewHolder(
            ItemHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelItemViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    fun setOnHotelClickListener(listener: HotelClickListener?) {
        clickListener = listener
    }

    override fun getItemCount(): Int {
        return count
    }

    inner class HotelItemViewHolder(var binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Hotel){
            adapter = HotelItemImagesAdapter(item.images)

            binding.run {
                nameHotel.text = item.name
                priceHotel.text = "${item.price}  ₽"

                stars.rating = 4.0f
                vpImages.adapter = adapter
                TabLayoutMediator(dotsIndicator, vpImages) { tab, position ->
                }.attach()
            }

            binding.cont.setOnClickListener {
                clickListener?.invoke(position,1)
            }
        }
    }
}