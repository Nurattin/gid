package com.travel.gid.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.travel.gid.R
import com.travel.gid.data.models.CategoriesHome
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.adapters.banner_adapter.ViewPagerAdapter
import com.travel.gid.ui.home.adapters.categories_adapter.CategoriesAdapter
import com.travel.gid.ui.home.adapters.ViewPagerChildFragmentsAdapter
import com.travel.gid.ui.home.adapters.banner_adapter.view_pager_extension.autoScrollViewPager
import com.travel.gid.ui.home.adapters.banner_adapter.view_pager_extension.infinityScrollViewPager
import com.travel.gid.ui.home.child_fragments.direction_fragment.DirectionsFragment
import com.travel.gid.ui.home.child_fragments.event_fragment.EventFragment
import com.travel.gid.ui.home.child_fragments.gid_fragment.GidFragment
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.ui.select_guest.BottomSheetSelectGuests
import com.travel.gid.utils.makeGoneIfElseVisible
import com.travel.gid.utils.makeVisibleIfElseGone
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var sliderHandler = Handler()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var childFragmentsAdapter: ViewPagerChildFragmentsAdapter
    private var listFragment = listOf(DirectionsFragment(), GidFragment(), EventFragment())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.run {

            childFragmentsAdapter =
                ViewPagerChildFragmentsAdapter(this@HomeFragment, listFragment)
            vpChildFragment.adapter = childFragmentsAdapter
            vpChildFragment.isUserInputEnabled = false

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.run {
            val adapters = ViewPagerAdapter()
            adapters.data = listOf(
                "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
                "https://generisonline.com/wp-content/uploads/2022/05/GettyImages-187613060-2c4df95dc90045d484a01dfe21880c40.jpg",
                "https://s28943.pcdn.co/wp-content/uploads/2018/04/Tour-Cart-Hero.jpg",
                "https://www.peruhop.com/wp-content/uploads/IMG_20180718_082918-2.jpg",
            )
            bannerViewPager.run {
                autoScrollViewPager(adapters)
                infinityScrollViewPager()

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        sliderHandler.removeCallbacks(sliderRunnable)
                        sliderHandler.postDelayed(sliderRunnable, 10000)
                    }
                })
            }
        }

        with(binding.categoriesAdapter) {
            val adapterCategories = CategoriesAdapter()
            val listCategories = listOf(
                CategoriesHome(
                    id = 0,
                    iconChecked = R.drawable.ic_globe,
                    iconUnChecked = R.drawable.ic_globe_uncheck,
                    name = "Интересное"
                ),
                CategoriesHome(
                    id = 1,
                    iconChecked = R.drawable.ic_gid_check,
                    iconUnChecked = R.drawable.ic_gid_uncheck,
                    name = "Гиды"
                ),
                CategoriesHome(
                    id = 2,
                    iconChecked = R.drawable.ic_place_check,
                    iconUnChecked = R.drawable.ic_place_uncheck,
                    name = "События"
                ),
                CategoriesHome(
                    id = 3,
                    iconChecked = R.drawable.ic_bascket_check,
                    iconUnChecked = R.drawable.ic_bascket_uncheck,
                    name = "Еда"
                )
            )
            adapterCategories.nowPosition = viewModel.currentItem
            adapterCategories.lastPosition = viewModel.lastItem

            adapterCategories.data = listCategories
            adapter = adapterCategories
            setHasFixedSize(true)
            binding.vpChildFragment.currentItem = viewModel.currentItem
            adapterCategories.setOnCategoriesClickListener {
                binding.vpChildFragment.currentItem = it
                viewModel.currentItem = it
                viewModel.lastItem = it
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }

        binding.refresh.setOnClickListener {
            refreshData()
        }
    }


    private fun refreshData() {
        val fragment: Fragment
        when (viewModel.currentItem) {

            0 -> {
                fragment = listFragment[0] as DirectionsFragment
                fragment.refreshData()
                fragment.setOnStopRefreshCallBack {
                    with(binding) {
                        swipeRefresh.isRefreshing = false
                        errorContent.visibility = View.GONE
                    }
                }

            }
            1 -> {

                fragment = listFragment[1] as GidFragment
                fragment.refreshData()
                fragment.setOnStopRefreshCallBack {
                    with(binding) {
                        swipeRefresh.isRefreshing = false


                        errorContent.isVisible = false
                        mainContent.isVisible = true
                    }
                }
                fragment.setOnShowErrorCallback {
                    showError()
                }
            }
        }
    }


    private fun setupRangePickerDialog() {
        val builder: MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> =
            MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Выберите дату заселения и выезда ")
        builder.setInputMode(INPUT_MODE_CALENDAR)
        builder.setTheme(R.style.MyMaterialCalendarTheme)

        val date = Date()
        date.date = date.date - 1
        val dateValidator: DateValidator = DateValidatorPointForward.from(date.time)

        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setValidator(dateValidator)
        try {
            builder.setCalendarConstraints(constraintsBuilder.build())

            val picker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>> = builder.build()
            getDateRange(picker)
            picker.show(parentFragmentManager, picker.toString())
        } catch (e: IllegalArgumentException) {
        }
    }

    private fun getDateRange(materialCalendarPicker: MaterialDatePicker<out androidx.core.util.Pair<Long, Long>>) {
        materialCalendarPicker.addOnPositiveButtonClickListener { selection: androidx.core.util.Pair<Long, Long> ->
            val startDate = selection.first
            val endDate = selection.second

//            binding.selectDates.text =
//                "${getDateFromTimestamp(startDate)}  ${getDateFromTimestamp(endDate)}"
        }
        materialCalendarPicker.addOnNegativeButtonClickListener { dialog: View? -> }

        materialCalendarPicker.addOnCancelListener { dialog: DialogInterface? ->

        }

    }

    private var sliderRunnable =
        Runnable { binding.bannerViewPager.currentItem = binding.bannerViewPager.currentItem + 1 }


    private fun showBanner() {

        binding.run {
            containerProgress.visibility = View.GONE
        }

    }

    fun showLoadingView() {
    }

    fun showDialogSelectGuests() {
        val bottomSheet = BottomSheetSelectGuests()
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun showError() {
        with(binding) {
            mainContent.visibility = View.GONE
            errorContent.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
        }
    }

}