package com.travel.gid.ui.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CompoundButton
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.travel.gid.R
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.FilterParamsTour
import com.travel.gid.databinding.FragmentFilterBinding
import com.travel.gid.utils.removeSelected
import com.travel.gid.utils.selected
import dagger.hilt.android.AndroidEntryPoint

internal typealias OnBtnApplyClickListener = ((FilterParamsTour) -> Unit)

@AndroidEntryPoint
class FilterFragmentSheet : BottomSheetDialogFragment() {

    var filter = FilterParamsTour()

    private var priceFrom: Int? = null
    private var priceTo: Int? = null

    private var priceFromNow: Int = 0
    private var priceToNow: Int = 0

    private val viewModel: FilterViewModel by viewModels()
    lateinit var binding: FragmentFilterBinding
    private var clickListener: OnBtnApplyClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        binding = FragmentFilterBinding.bind(view)

        viewModel.filters.value = filter

        viewModel.filters.observe(viewLifecycleOwner) {
            displayScreen()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            startPrice.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (!startPrice.text.isNullOrEmpty()) {
                        changePriceFrom(startPrice.text.toString().toInt())
                    }
                }
            }

            endPrice.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (!endPrice.text.isNullOrEmpty()) {
                        changePriceEnd(endPrice.text.toString().toInt())
                    }
                }
            }

            startPrice.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus()
                }
                false
            }


            endPrice.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus()
                }
                false
            }

            rangeSliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, fromUser ->
                if (fromUser) {
                    priceFromNow = slider.values[0].toInt()
                    priceToNow = slider.values[1].toInt()
                    startPrice.setText(priceFromNow.toString())
                    endPrice.setText(priceToNow.toString())
                }
            })

            applyBtn.setOnClickListener {
                val categories = chipGroup.checkedChipIds
                putFilterDetail(
                    categories,
                    priceFromNow,
                    priceToNow,
                )
            }

        }
    }

    private fun changePriceEnd(number: Int) {
        priceToNow =
            if (number < priceFromNow) priceFromNow else if (number > priceTo!!) priceTo!! else number
        binding.endPrice.setText(priceToNow.toString())
        changeRangeSlide(priceToNow, priceFromNow)
    }

    private fun changePriceFrom(number: Int) {
        priceFromNow =
            if (number > priceToNow) priceToNow else if (number < priceFrom!!) priceFrom!! else number
        binding.startPrice.setText(priceFromNow.toString())
        changeRangeSlide(priceToNow, priceFromNow)
    }

    private fun changeRangeSlide(startValue: Int, endValue: Int) {
        binding.apply {
            rangeSliderPrice.values =
                listOf(startValue.toFloat(), endValue.toFloat())
        }
    }

    private fun displayScreen(
    ) {
        viewModel.filters.value?.let {
            it.categoriesName?.let {listCategories ->
                for (element in listCategories) {
                    val chip =
                        layoutInflater.inflate(
                            R.layout.single_chip,
                            binding.chipGroup,
                            false
                        ) as Chip
                    chip.setOnCheckedChangeListener { compoundButton, b ->
                        selectAllIfPressedOtherwiseRemoveTheSelection(compoundButton, b)
                    }
                    chip.text = element.name
                    chip.id = if (element.name == "Все") -1 else element.id.toInt()
                    chip.isChecked = filter.categoriesAccept?.contains(element.id.toInt()) ?: false
                    binding.chipGroup.addView(chip)
                }
            }
            priceFrom = if (priceFrom == null) it.startPrice else priceFrom
            priceTo = if (priceTo == null) it.endPrice else priceTo

            priceToNow = it.endPrice ?: priceTo!!
            priceFromNow = it.startPrice ?: priceFrom!!

            binding.rangeSliderPrice.values = listOf(priceFromNow.toFloat(), priceToNow.toFloat())
            binding.rangeSliderPrice.valueFrom = priceFrom!!.toFloat()
            binding.rangeSliderPrice.valueTo = priceTo!!.toFloat()

            binding.endPrice.setText(priceToNow.toString())
            binding.startPrice.setText(priceFromNow.toString())
        }

    }

    private fun selectAllIfPressedOtherwiseRemoveTheSelection(
        compoundButton: CompoundButton,
        isChecked: Boolean
    ) {
        if (compoundButton.text == "Все" && isChecked) {
            binding.chipGroup.forEach {
                it as Chip
                it.removeSelected()
            }
            val chipAll = binding.chipGroup.getChildAt(0) as Chip
            chipAll.selected()
        } else {
            val chipAll = binding.chipGroup.getChildAt(0) as Chip
            chipAll.removeSelected()
        }
    }

    private fun putFilterDetail(
        categories: List<Int>?,
        startPrice: Int,
        endPrice: Int,
    ) {
        filter.categoriesAccept = categories
        filter.startPrice = startPrice
        filter.endPrice = endPrice
        clickListener?.invoke(filter)
        this.dismiss()
    }

    companion object {
        const val TAG = "com.travel.gid.ui.filter.FilterFragmentSheet"
    }

    fun setOnBtnApplyClickListener(listener: OnBtnApplyClickListener?) {
        clickListener = listener
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
}