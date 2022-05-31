package com.travel.gid.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.travel.gid.R
import com.travel.gid.data.Resource
import com.travel.gid.data.datasource.network.ApiResponse
import com.travel.gid.data.datasource.network.request
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.DirectionData
import com.travel.gid.data.models.Tour
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.adapters.ViewPagerChildFragmentsAdapter
import com.travel.gid.ui.select_guest.BottomSheetSelectGuests
import com.travel.gid.utils.getDateFromTimestamp
import com.travel.gid.utils.home_btns_controller.HomeButtonsControllerImpl
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: HomeFragmentBinding
    lateinit var homeButtonsControllerImpl: HomeButtonsControllerImpl


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //observeViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.directionsLiveDataPrivate.observe(viewLifecycleOwner) {
            it.body()?.data?.let { listDirection ->
                showDirections(listDirection)
                showBanner()
            }
        }

        viewModel.tourLiveDataPrivate.observe(viewLifecycleOwner) {
            it.body()?.data.let { tourList ->

            }
        }

        binding.run {
            homeButtonsControllerImpl = HomeButtonsControllerImpl(requireContext())
            homeButtonsControllerImpl.prevContainer = interestContainer
            vpChildFragment.offscreenPageLimit = 2
            selectGuest.setOnClickListener {
                showDialogSelectGuests()
            }

            selectCity.setOnClickListener {
                findNavController().navigate(R.id.selectCityFragment)
            }

            findHotel.setOnClickListener {
                findNavController().navigate(R.id.hotelsFragment)
            }
            selectDates.setOnClickListener {
                setupRangePickerDialog()
            }

            interestContainer.setOnClickListener {
                homeButtonsControllerImpl.setBtnStyleChecked(it, 0)
                vpChildFragment.setCurrentItem(0, false)
            }

            gidContainer.setOnClickListener {
                homeButtonsControllerImpl.setBtnStyleChecked(it, 1)
                vpChildFragment.setCurrentItem(1, false)
            }


            placesBtn.setOnClickListener {
                homeButtonsControllerImpl.setBtnStyleChecked(it, 2)
                vpChildFragment.setCurrentItem(2, false)
            }
//
//            souvenirsBtn.setOnClickListener {
//                homeButtonsControllerImpl.setBtnStyleChecked(it, 3)
//                vpChildFragment.setCurrentItem(2, false)
//            }
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

            binding.selectDates.text =
                "${getDateFromTimestamp(startDate)}  ${getDateFromTimestamp(endDate)}"
        }
        materialCalendarPicker.addOnNegativeButtonClickListener { dialog: View? -> }

        materialCalendarPicker.addOnCancelListener { dialog: DialogInterface? ->

        }

    }

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

        binding.run {

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

//    fun observeViewModel(){
//        observe(viewModel.directionsLiveData, ::handleDirectionsList)
//    }
}