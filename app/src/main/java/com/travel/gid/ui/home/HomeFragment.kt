package com.travel.gid.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.travel.gid.databinding.HomeFragmentBinding
import com.travel.gid.ui.home.adapters.ViewPagerChildFragmentsAdapter
import com.travel.gid.ui.home.adapters.banner_adapter.ViewPagerAdapter
import com.travel.gid.ui.home.adapters.banner_adapter.view_pager_extension.autoScrollViewPager
import com.travel.gid.ui.home.adapters.banner_adapter.view_pager_extension.infinityScrollViewPager
import com.travel.gid.ui.home.adapters.categories_adapter.CategoriesAdapter
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.ConnectionLiveData
import com.travel.gid.utils.checkInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var networkConnection: ConnectionLiveData
    private val viewModel: HomeViewModel by viewModels()
    private var sliderHandler = Handler(Looper.getMainLooper())
    private lateinit var binding: HomeFragmentBinding
    private val adapters = ViewPagerAdapter()
    private val adapterCategories = CategoriesAdapter()
    private lateinit var childFragmentsAdapter: ViewPagerChildFragmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.run {
            childFragmentsAdapter =
                ViewPagerChildFragmentsAdapter(this@HomeFragment)
            vpChildFragment.adapter = childFragmentsAdapter
            vpChildFragment.isUserInputEnabled = false
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            viewModel.listImageBanner.observe(viewLifecycleOwner) {
                adapters.data = it
            }
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

        with(binding) {
            categoriesAdapter.adapter = adapterCategories
            viewModel.listCategoriesHome.observe(viewLifecycleOwner) { listCategoriesHome ->
                adapterCategories.differ.submitList(listCategoriesHome)
                binding.vpChildFragment.currentItem = viewModel.currentItem
            }
            adapterCategories.setOnCategoriesClickListener { newPosition ->
                with(viewModel) {
                    if (currentItem != newPosition) {
                        changePosCategories(currentItem, newPosition)
                        swipeProgress.value = false
                    }
                }
            }
        }

        viewModel.swipeProgress.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {

            networkConnection.checkInternetConnection(
                actionOnInternetConnection = {
                    viewModel.swipeProgress.value = true
                    refreshData()
                },
                actionOnInternetDisconnection = {
                    viewModel.swipeProgress.value = true
                    refreshData()
                })

        }
    }


    private fun refreshData() {
        when (viewModel.currentItem) {
            0 -> {
//                viewModel.getDataDirectionFragmentScreen()
            }
            1 -> {
                viewModel.getGidList()
            }
            2 -> {
                viewModel.getEvent()
            }
        }
    }


    private var sliderRunnable =
        Runnable { binding.bannerViewPager.currentItem = binding.bannerViewPager.currentItem + 1 }
}