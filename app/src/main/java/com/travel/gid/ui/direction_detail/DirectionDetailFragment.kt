package com.travel.gid.ui.direction_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.travel.gid.R
import com.travel.gid.data.datasource.network.ApiResponse
import com.travel.gid.data.datasource.network.request
import com.travel.gid.data.models.DirectionDetail
import com.travel.gid.ui.ActivityProviding
import com.travel.gid.ui.direction_detail.adapters.TourDirectionAdapter
import com.travel.gid.ui.tour_detail.TourDetailFragmentArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.travel.gid.data.models.City
import com.travel.gid.data.models.DirectionData
import com.travel.gid.databinding.FragmentDirectionDetailBinding
import com.travel.gid.ui.direction_detail.adapters.DirectionImageAdapter
import com.travel.gid.ui.direction_detail.adapters.SimilarDirectionListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DirectionDetailFragment : Fragment() {

    private val navController by lazy { (activity as ActivityProviding).provideNavController() }
    private val args by lazy { DirectionDetailFragmentArgs.fromBundle(requireArguments()) }

    lateinit var binding : FragmentDirectionDetailBinding

    private val viewModel: DirectionDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDirectionDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            getDirectionDetail()
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.directionDetail != null) {
            initBanner()
            initTours()
            initDescr()
            initSimilarTours()
        }

    }

    private suspend fun getDirectionDetail(){
        when(val response = request { viewModel.getDirectionDetail(args.id) }) {
            is ApiResponse.Result<*> -> {
                val directionDetail = response.data as DirectionDetail

                viewModel.directionDetail = directionDetail
                initBanner()
                initTours()
                initDescr()
                initSimilarTours()
            }

            is ApiResponse.Error -> {

            }
        }
    }

    private fun initBanner() {

        binding.run {
            containerProgress.visibility = View.GONE
        }

        binding.run {
            val adapter = DirectionImageAdapter(viewModel.directionDetail!!.data.images)
            photosViewpager.adapter = adapter
            TabLayoutMediator(wormDotsIndicator, photosViewpager) { tab, position ->
            }.attach()
        }
    }

    private fun initDescr(){
        binding.run {
            directionContent.run {
                textDescr.text = viewModel.directionDetail!!.data.description
            }
        }
    }

    private fun initTours() {
        binding.run {
            directionContent.run {

                val adapter = TourDirectionAdapter(viewModel.directionDetail!!.data.tours)

                adapter.setOnTourClickListener {id ->
                    navController.navigate(
                        R.id.tourDetailFragment,
                        TourDetailFragmentArgs(id).toBundle(),
                        null
                    )
                }
                categoriesRecyclerView.adapter = adapter
            }
        }
    }


    private fun initSimilarTours() {
        binding.run {
            directionContent.run {

                val listTours = ArrayList<DirectionData>()

                listTours.add(DirectionData(1,
                    "Туры в Дербент",
                    "https://static.mk.ru/upload/entities/2020/02/19/12/articles/facebookPicture/51/66/9f/50/a4142202430107305e84190063857f20.jpg",
                City(1, "г. Дербент")))

                listTours.add(DirectionData(1,
                    "Горные туры",
                    "https://trikonia.ru/up/20210928-515683368.jpeg",
                    City(1, "с. Сулак")))

                listTours.add(DirectionData(1,
                    "Туры с морем",
                    "https://pbs.twimg.com/media/Cua1g6-WYAAka12.jpg",
                    City(1, "г. Каспийск")))


                val adapter = SimilarDirectionListAdapter(listTours)

                similarDirectionsRecyclerView.adapter = adapter
            }
        }
    }


}