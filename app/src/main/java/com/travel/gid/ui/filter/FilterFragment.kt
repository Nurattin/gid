package com.travel.gid.ui.direction_list.list_tour

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.travel.gid.R
import com.travel.gid.data.models.Categories
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
            for (element in listCategories) {
                val chip = layoutInflater.inflate(R.layout.single_chip, binding.chipGroup, false) as Chip
                chip.text = element.name
                if (element.name == "Все") chip.isChecked = true
                binding.chipGroup.addView(chip)
            }

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
        binding.toolbar.setNavigationOnClickListener { this.dismiss() }
        binding.toolbar.setOnMenuItemClickListener { true }

        val listCategories =
            listOf("Все", "Музей", "Пляж", "Горы", "Каньон", "Заповедник", "Кемпинг")
        for (element in listCategories) {
            val chip =
                layoutInflater.inflate(R.layout.single_chip, binding.chipGroup, false) as Chip
            chip.text = element
            if (element == "Все") {
                chip.isChecked = true
                chipAll = chip
            }
            binding.chipGroup.addView(chip)
        }
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
//            tvStartPrice.setOnEditorActionListener { _, actionId, _ ->
////                priceFrom.error = null
//                try {
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//
//                        if (tvStartPrice.text.toString().toInt() > rangeSliderPrice.valueTo) {
//                            tvStartPrice.setText(rangeSliderPrice.valueTo.toString())
////                            priceFrom.error = "макс ${rangeSliderPrice.valueTo}"
//                        } else if (tvStartPrice.text.toString()
//                                .toInt() < rangeSliderPrice.valueFrom
//                        ) {
//                            tvStartPrice.setText(rangeSliderPrice.valueFrom.toString())
////                            priceFrom.error = "мин ${rangeSliderPrice.valueFrom}"
//                        } else if (tvStartPrice.text.toString().toInt() > tvEndPrice.text.toString()
//                                .toInt()
//                        ) {
////                            priceFrom.error = null
//                            tvStartPrice.text = tvEndPrice.text
//                            if (tvEndPrice.text == null) rangeSliderPrice.values = listOf(
//                                tvStartPrice.text.toString().toFloat(),
//                                rangeSliderPrice.valueTo
//                            )
//                            else changeRangeSlide(tvEndPrice.text as Editable, tvStartPrice.text as Editable)
//                        } else {
////                            priceFrom.error = null
//                            changeRangeSlide(tvEndPrice.text as Editable, tvStartPrice.text as Editable)
//                            tvStartPrice.clearFocus()
//                        }
//                    }
//                    false
//                } catch (e: Exception) {
//                    false
//                }
//            }

//            tvEndPrice.setOnEditorActionListener { _, actionId, _ ->
////                priceTo.error = null
//                try {
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        if (tvEndPrice.text.toString().toInt() > rangeSliderPrice.valueTo) {
//                            tvEndPrice.setText(rangeSliderPrice.valueTo.toString())
////                            priceTo.error = "макс ${rangeSliderPrice.valueTo}"
//                        } else if (tvEndPrice.text.toString().toInt() < rangeSliderPrice.valueFrom) {
//                            tvEndPrice.setText(rangeSliderPrice.valueFrom.toString())
////                            priceTo.error = "мин ${rangeSliderPrice.valueFrom}"
//                        } else if (tvEndPrice.text.toString().toInt() < tvStartPrice.text.toString()
//                                .toInt()
//                        ) {
////                            priceTo.error = null
//                            tvEndPrice.text = tvStartPrice.text
//                            if (tvStartPrice.text == null) rangeSliderPrice.values =
//                                listOf(rangeSliderPrice.valueFrom, tvEndPrice.toString().toFloat())
//                            else changeRangeSlide(tvEndPrice.text as Editable, tvStartPrice.text as Editable)
//                        } else {
//                            changeRangeSlide(tvEndPrice.text as Editable, tvStartPrice.text as Editable)
//                            tvEndPrice.clearFocus()
////                            priceTo.error = null
//                        }
//                    }
//                    false
//                } catch (e: Exception) {
//                    false
//                }
//            }

            rangeSliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
//                if (binding.priceTo.error != null || binding.priceFrom.error != null) {
//                    binding.priceTo.error = null
//                    binding.priceFrom.error = null
//                }
                tvStartPrice.setText(round(slider.values[0]).toString())
                tvEndPrice.setText(round(slider.values[1]).toString())

            })

            rangeSliderDay.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
//                if (binding.priceTo.error != null || binding.priceFrom.error != null) {
//                    binding.priceTo.error = null
//                    binding.priceFrom.error = null
//                }
                tvStartDaysCount.setText(round(slider.values[0]).toString())
                tvEndDaysCount.setText(round(slider.values[1]).toString())

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