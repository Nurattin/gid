package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.travel.gid.R
import com.travel.gid.databinding.FragmentFilterBinding
import com.travel.gid.ui.filter.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round

@AndroidEntryPoint
class FilterFragmentSheet : BottomSheetDialogFragment() {

    private val viewModel: FilterViewModel by viewModels()
    lateinit var binding: FragmentFilterBinding
    lateinit var chipAll: Chip

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        binding = FragmentFilterBinding.bind(view)

        viewModel.filters.observe(viewLifecycleOwner) {

            val listCategories = viewModel.getCategories()
            var i = 0
            for (element in listCategories) {
                val chip =
                    layoutInflater.inflate(R.layout.single_chip, binding.chipGroup, false) as Chip
                chip.text = element.name
                chip.id = element.id.toInt()
                if (element.name == "Все") chip.isChecked = true
                binding.chipGroup.addView(chip)
                i += 1

            }
//            binding.chipGroup.checkedChipIds

            val (price_from, price_to) = viewModel.getPriceRange()!!

            binding.rangeSliderPrice.values = listOf(price_from.toFloat(), price_to.toFloat())
            binding.rangeSliderPrice.valueFrom = price_from.toFloat()
            binding.rangeSliderPrice.valueTo = price_to.toFloat()
            stopProgressBar()
        }
        return binding.root
    }

    private fun stopProgressBar() {
        binding.apply {
            progressBar.visibility = View.GONE
            content.visibility = View.VISIBLE
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rangeSliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->

                tvStartPrice.text = round(slider.values[0]).toString()
                tvEndPrice.text = round(slider.values[1]).toString()

            })

            rangeSliderDay.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
                tvStartDaysCount.text = round(slider.values[0]).toString()
                tvEndDaysCount.text = round(slider.values[1]).toString()

            })


            applyBtn.setOnClickListener {
                val categories = chipGroup.checkedChipIds
                putFilterDetail(
                    categories,
                    tvStartPrice.text.toString(),
                    tvEndPrice.text.toString(),
                    rangeSliderDay.values
                )
            }

        }
    }

    private fun putFilterDetail(
        categories: List<Int>,
        startPrice: String,
        endPrice: String,
        dayCount: List<Float>,
    ) {
        val filterDetail = hashMapOf(
            "categories" to categories,
            "startPrice" to startPrice,
            "endPrice" to endPrice,
            "dayCount" to dayCount
        )
        Log.i("dwa", "$filterDetail")
    }

    private fun changeRangeSlide(startValue: Editable?, endValue: Editable?) {
        binding.apply {
            rangeSliderPrice.values =
                listOf(startValue.toString().toFloat(), endValue.toString().toFloat())
        }
    }

    companion object {
        const val TAG = "FilterFragmentSheet"
    }
}