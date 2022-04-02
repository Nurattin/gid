package com.travel.gid.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.travel.gid.data.Resource
import com.travel.gid.data.datasource.network.ApiResponse
import com.travel.gid.data.datasource.network.request
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.DirectionData
import com.travel.gid.data.models.Tour
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.adapters.BannerListAdapter
import com.travel.gid.ui.home.adapters.ViewPagerChildFragmentsAdapter
import com.travel.gid.utils.home_btns_controller.HomeButtonsControllerImpl
import com.travel.gid.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.run {
            PagerSnapHelper().attachToRecyclerView(bannerRecyclerView)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.tour == null) {
            viewModel.getDirections()
            lifecycleScope.launch {
                getTours()
            }
        }else {
            showDirections()
            showBanner()
        }

        binding.run {
            homeButtonsControllerImpl = HomeButtonsControllerImpl(requireContext())
            homeButtonsControllerImpl.prevContainer = interestContainer

            interestContainer.setOnClickListener {
                homeButtonsControllerImpl.setBtnStyleChecked(it, 0)
                vpChildFragment.setCurrentItem(0, false)
            }

            gidContainer.setOnClickListener {
                homeButtonsControllerImpl.setBtnStyleChecked(it, 1)
                vpChildFragment.setCurrentItem(1, false)
            }

//            placesBtn.setOnClickListener {
//                homeButtonsControllerImpl.setBtnStyleChecked(it, 2)
//                vpChildFragment.setCurrentItem(2, false)
//            }
//
//            souvenirsBtn.setOnClickListener {
//                homeButtonsControllerImpl.setBtnStyleChecked(it, 3)
//                vpChildFragment.setCurrentItem(2, false)
//            }
        }
    }

    private suspend fun getTours(){
        when(val response = request { viewModel.getTour() }) {
            is ApiResponse.Result<*> -> {
                val data = response.data as Tour
                viewModel.tour = data
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
            bannerRecyclerView.run {
                val adapterVH = BannerListAdapter(viewModel.tour!!.data)

//                adapterVH.setOnTourClickListener {
//                    startActivity(Intent(context, TourDirectionActivity::class.java))
//                }

                adapter = adapterVH

                clipToPadding = false
                clipChildren = false
            }
        }
    }

    private fun showDirections() {

        binding.run {
            val childFragmentsAdapter =
                ViewPagerChildFragmentsAdapter(this@HomeFragment, directions = viewModel.directions!!.data)
            vpChildFragment.adapter = childFragmentsAdapter
            vpChildFragment.isUserInputEnabled = false
        }
    }

    fun showLoadingView(){

    }

    private fun handleDirectionsList(status: Resource<Direction>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { showDirections() }
            is Resource.DataError -> {

            }
        }
    }

    fun observeViewModel(){
        observe(viewModel.tourLiveData, ::handleDirectionsList)
    }
}