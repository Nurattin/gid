package com.travel.gid.ui.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.travel.gid.R
import com.travel.gid.data.models.FilterParams
import com.travel.gid.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

internal typealias OnBtnApplyClickListener = ((FilterParams) -> Unit)

@AndroidEntryPoint
class FilterFragmentSheet : BottomSheetDialogFragment() {

    var filterDetail = FilterParams()

    private var priceFrom: Int = 0
    private var priceTo: Int = 0

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
        showProgress()

        viewModel.getFilter()
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
            if (number < priceFromNow) priceFromNow else if (number > priceTo) priceTo else number
        binding.endPrice.setText(priceToNow.toString())
        changeRangeSlide(priceToNow, priceFromNow)
    }

    private fun changePriceFrom(number: Int) {
        priceFromNow =
            if (number > priceToNow) priceToNow else if (number < priceFrom) priceFrom else number
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
        val listCategories = viewModel.getCategories()
        for (element in listCategories!!) {
            val chip =
                layoutInflater.inflate(R.layout.single_chip, binding.chipGroup, false) as Chip
            chip.text = element.name
            chip.id = element.id.toInt()
            chip.isChecked = filterDetail.categories?.contains(element.id.toInt()) ?: false
            binding.chipGroup.addView(chip)
        }

        priceFrom = viewModel.getPriceRange()!!.priceFrom
        priceTo = viewModel.getPriceRange()!!.priceTo

        priceToNow = filterDetail.endPrice ?: priceTo
        priceFromNow = filterDetail.startPrice ?: priceFrom

        binding.rangeSliderPrice.values = listOf(priceFromNow.toFloat(), priceToNow.toFloat())
        binding.rangeSliderPrice.valueFrom = priceFrom.toFloat()
        binding.rangeSliderPrice.valueTo = priceTo.toFloat()

        binding.endPrice.setText(priceToNow.toString())
        binding.startPrice.setText(priceFromNow.toString())
        stopProgressBar()
    }

    private fun putFilterDetail(
        categories: List<Int>,
        startPrice: Int,
        endPrice: Int,
    ) {
        filterDetail.categories = categories
        filterDetail.startPrice = startPrice
        filterDetail.endPrice = endPrice
        clickListener?.invoke(filterDetail)
        this.dismiss()
    }

    companion object {
        const val TAG = "com.travel.gid.ui.filter.FilterFragmentSheet"
    }

    fun setOnBtnApplyClickListener(listener: OnBtnApplyClickListener?) {
        clickListener = listener
    }

    private fun stopProgressBar() {
        binding.apply {
            progressBar.visibility = View.GONE
            content.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            content.visibility = View.GONE
        }
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