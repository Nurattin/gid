package com.travel.gid.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.travel.gid.R
import com.travel.gid.data.Resource
import com.travel.gid.data.datasource.network.ApiResponse
import com.travel.gid.data.datasource.network.request
import com.travel.gid.data.models.CategoriesHome
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.DirectionData
import com.travel.gid.data.models.Tour
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.adapters.BannerViewPagerAdapter.ViewPagerAdapter
import com.travel.gid.ui.home.adapters.CategoriesAdapter.CategoriesAdapter
import com.travel.gid.ui.home.adapters.ViewPagerChildFragmentsAdapter
import com.travel.gid.ui.select_guest.BottomSheetSelectGuests
import com.travel.gid.utils.home_btns_controller.HomeButtonsControllerImpl
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.abs


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var sliderHandler = Handler()
    private lateinit var binding: HomeFragmentBinding
    lateinit var homeButtonsControllerImpl: HomeButtonsControllerImpl


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.directionsLiveDataPrivate.observe(viewLifecycleOwner) {
            it.body()?.data?.let { listDirection ->
                showDirections(listDirection)
                showBanner()
            }
        }

        binding.run {
            val adapters = ViewPagerAdapter()
            adapters.data = listOf(
                "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
                "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
                "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
            )
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(30))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
            bannerViewPager.run {
                adapter = adapters
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setPageTransformer(compositePageTransformer)

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        sliderHandler.removeCallbacks(sliderRunnable)
                        sliderHandler.postDelayed(sliderRunnable, 5000)
                    }
                })
            }
        }

        with(binding.categoriesAdapter) {
            val adapterCategories = CategoriesAdapter()
            val listCategories = listOf<CategoriesHome>(
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
            adapterCategories.data = listCategories
            adapter = adapterCategories
            setHasFixedSize(true)
            adapterCategories.setOnCategoriesClickListener {
                binding.vpChildFragment.setCurrentItem(it, true)
            }
        }
    }


    private fun setupRangePickerDialog() {
        val builder: MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> =
            MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Выберите дату заселения и выезда")
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

    private suspend fun getTours() {
        when (val response = request { viewModel.getTour() }) {
            is ApiResponse.Result<*> -> {
                val data = response.data as Tour
                showBanner()
            }
            is ApiResponse.Error -> {

            }
        }
    }

    private fun showBanner() {

        binding.run {
            containerProgress.visibility = View.GONE
        }

    }

    private fun showDirections(listDirection: List<DirectionData>) {

        binding.run {
            val childFragmentsAdapter =
                ViewPagerChildFragmentsAdapter(this@HomeFragment, directions = listDirection)
            vpChildFragment.adapter = childFragmentsAdapter
            vpChildFragment.isUserInputEnabled = false
        }
    }

    fun showLoadingView() {
    }

    fun showDialogSelectGuests() {
        var bottomSheet = BottomSheetSelectGuests()
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    private fun handleDirectionsList(status: Resource<Direction>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { showDirections(it.data) }
            is Resource.DataError -> {

            }
        }
    }
}