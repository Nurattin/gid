package com.travel.gid.ui.filter

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
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {

    private val viewModel: FilterViewModel by viewModels()
    lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
//        binding.toolbar.setNavigationOnClickListener { this.dismiss() }
//        binding.toolbar.setOnMenuItemClickListener { true }+
        super.onViewCreated(view, savedInstanceState)




        binding.apply {

            startPrice.setOnEditorActionListener { _, actionId, _ ->
                priceFrom.error = null
                try {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (startPrice.text.toString().toInt() > rangeSliderPrice.valueTo) {
                            startPrice.setText(rangeSliderPrice.valueTo.toString())
                            priceFrom.error = "макс ${rangeSliderPrice.valueTo}"
                        } else if (startPrice.text.toString()
                                .toInt() < rangeSliderPrice.valueFrom
                        ) {
                            startPrice.setText(rangeSliderPrice.valueFrom.toString())
                            priceFrom.error = "мин ${rangeSliderPrice.valueFrom}"
                        } else if (startPrice.text.toString().toInt() > endPrice.text.toString()
                                .toInt()
                        ) {
                            priceFrom.error = null
                            startPrice.text = endPrice.text
                            if (endPrice.text == null) rangeSliderPrice.values = listOf(
                                startPrice.text.toString().toFloat(),
                                rangeSliderPrice.valueTo
                            )
                            else changeRangeSlide(endPrice.text, startPrice.text)
                        } else {
                            priceFrom.error = null
                            changeRangeSlide(endPrice.text, startPrice.text)
                            startPrice.clearFocus()
                        }
                    }
                    false
                } catch (e: Exception) {
                    false
                }
            }

            endPrice.setOnEditorActionListener { _, actionId, _ ->
                priceTo.error = null
                try {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (endPrice.text.toString().toInt() > rangeSliderPrice.valueTo) {
                            endPrice.setText(rangeSliderPrice.valueTo.toString())
                            priceTo.error = "макс ${rangeSliderPrice.valueTo}"
                        } else if (endPrice.text.toString().toInt() < rangeSliderPrice.valueFrom) {
                            endPrice.setText(rangeSliderPrice.valueFrom.toString())
                            priceTo.error = "мин ${rangeSliderPrice.valueFrom}"
                        } else if (endPrice.text.toString().toInt() < startPrice.text.toString()
                                .toInt()
                        ) {
                            priceTo.error = null
                            endPrice.text = startPrice.text
                            if (startPrice.text == null) rangeSliderPrice.values =
                                listOf(rangeSliderPrice.valueFrom, endPrice.toString().toFloat())
                            else changeRangeSlide(endPrice.text, startPrice.text)
                        } else {
                            changeRangeSlide(endPrice.text, startPrice.text)
                            endPrice.clearFocus()
                            priceTo.error = null
                        }
                    }
                    false
                } catch (e: Exception) {
                    false
                }
            }
            rangeSliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
                if (binding.priceTo.error != null || binding.priceFrom.error != null) {
                    binding.priceTo.error = null
                    binding.priceFrom.error = null
                }
                startPrice.setText(round(slider.values[0]).toString())
                endPrice.setText(round(slider.values[1]).toString())

            })
            applyBtn.setOnClickListener {
                val categories = chipGroup.checkedChipIds
                putFilterDetail(
                    categories,
                    startPrice.text.toString(),
                    endPrice.text.toString(),
                    rangeSliderDay.values
                )
            }

        }
    }

    private fun putFilterDetail(
        categories: List<Int>,
        startPrice: String,
        endPrice: String,
        dayCount: List<Float>
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