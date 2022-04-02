package com.travel.gid.ui.tour_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.travel.gid.data.models.Place
import com.travel.gid.data.models.TourData
import com.travel.gid.ui.tour_detail.adapters.ToursImageAdapter
import com.travel.gid.utils.CustomPointer
import com.google.android.material.tabs.TabLayoutMediator
import com.travel.gid.R
import com.travel.gid.data.datasource.network.ApiResponse
import com.travel.gid.data.datasource.network.request
import com.travel.gid.data.models.TourDetail
import com.travel.gid.databinding.FragmentTourDetailBinding
import com.travel.gid.ui.direction_detail.DirectionDetailFragmentArgs
import com.travel.gid.ui.tour_detail.adapters.IncludedTour
import com.travel.gid.ui.tour_detail.adapters.IncludedTourAdapter
import com.travel.gid.utils.valueToPrice
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TourDetailFragment : Fragment() {

    private val args by lazy { TourDetailFragmentArgs.fromBundle(requireArguments()) }

    private val viewModel: ToutDetailViewModel by viewModels()

    lateinit var binding: FragmentTourDetailBinding

    private val arrayPlaces =
        arrayListOf(
            Place(title = "Махачкала", coordinate = Point(42.96663, 47.51263)),
            Place(title = "Буйнакск", coordinate = Point(42.819, 47.1192)),
            Place(title = "Леваши", coordinate = Point(42.431739, 47.322962)),
            Place(title = "Дербент", coordinate = Point(42.057669, 48.288776))
        )

    private fun getTours() =
        arrayListOf(
            TourData(1, "Горная местность", "http://api.gidtravel.xyz/image?path=tours%2Fkanon-foto.jpg"),
            TourData(2, "Горная местность", "http://api.gidtravel.xyz/image?path=tours%2Fkanon-foto.jpg"),
            TourData(3, "Горная местность", "http://api.gidtravel.xyz/image?path=tours%2Fkanon-foto.jpg"),
            TourData(4, "Горная местность", "http://api.gidtravel.xyz/image?path=tours%2Fkanon-foto.jpg"),
        )

    private var mapObjects: MapObjectCollection? = null

    //view
    private lateinit var toolbar: Toolbar
    private lateinit var includeDetailTour : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            getTourDetail()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.tourDetail != null){
            initBanner()
            initDescription()
            initIncludedTour()
        }

        setupMap()
        initMarkPlaces()
        submitRequest()
    }

    @DelicateCoroutinesApi
    private suspend fun getTourDetail(){
        when(val response = request { viewModel.getTourDetail(args.id) }) {
            is ApiResponse.Result<*> -> {
                val tourDetail = response.data as TourDetail

                viewModel.tourDetail = tourDetail

                initBanner()
                initDescription()
                initIncludedTour()

                GlobalScope.run {
                    delay(1000)
                    binding.run {
                        containerProgress.visibility = View.GONE
                    }
                }
            }

            is ApiResponse.Error -> {

            }
        }
    }

    private fun initBanner() {
        binding.run {

            val adapter: ToursImageAdapter = ToursImageAdapter(viewModel.tourDetail!!.data.detailPhoto)
            bannerVP.adapter = adapter

            TabLayoutMediator(wormDotsIndicator, bannerVP) { tab, position ->

            }.attach()
        }
    }

    private fun initDescription() {
        binding.run {
            val dataTour = viewModel.tourDetail!!.data

            priceTour.text =  valueToPrice(dataTour.price)

            buyTourButton.setOnClickListener {

            }

            detailTourInclude.run {

                textPrice.text = valueToPrice(dataTour.price)
                txtNameTour.text = viewModel.tourDetail!!.data.name
                txtDuration.text = dataTour.duration
                txtCount.text = dataTour.peopleCount.toString() + " человек"
                expandTextView.text = viewModel.tourDetail!!.data.description

                if (expandableText.lineCount < 4){
                  //  containerMore.visibility = View.GONE
                }

                moreText.setOnClickListener {
                    expandTextView.onClick(expandTextView)
                }
            }

            buyTourButton.setOnClickListener {
                findNavController().navigate(
                    R.id.fragmentReservation
                )
            }
        }
    }

    private fun initIncludedTour(){
        val array = ArrayList<IncludedTour>()
        array.add(IncludedTour(R.drawable.ic_car, "Транспорт"))
        array.add(IncludedTour(R.drawable.ic_baidar, "Байдарки"))
        array.add(IncludedTour(R.drawable.ic_food, "Питание"))
        array.add(IncludedTour(R.drawable.ic_mountin, "Горы"))

        val adapter = IncludedTourAdapter(array)

        binding.run {
            detailTourInclude.run {
                recIncludedTour.adapter = adapter
            }
        }
    }

    private fun setupSimilarTours() {
//        binding.run {
//            detailTourInclude.similarToursRecyclerView.run {
//                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                val adapter = DirectionsListAdapter(array = getTours())
//                this.adapter = adapter
//
//                //   adapter.setOnTourClickListener { startActivity(Intent(this, MapActivity::class.java)) }
//                clipToPadding = false
//                addItemDecoration(
//                    SpaceItemDecoration(
//                        space = 50,
//                        orientation = SpaceItemDecoration.Orientation.HORIZONTAL
//                    )
//                )
//            }
//        }
    }



    private fun setupMap() {
        binding.run {
            detailTourInclude.run {
                mapview.map.isRotateGesturesEnabled = false
                val boundingBox = BoundingBox(
                    Point(arrayPlaces[0].coordinate.latitude, arrayPlaces[0].coordinate.longitude),
                    Point(41.629875, 48.680262)
                )
                var cameraPosition = mapview.map.cameraPosition(boundingBox)
                cameraPosition = CameraPosition(
                    cameraPosition.target,
                    cameraPosition.zoom - 1f,
                    cameraPosition.azimuth,
                    cameraPosition.tilt
                )
                mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 0f), null)
                mapview.setNoninteractive(true)
            }
        }

    }

    private fun initMarkPlaces() {
        binding.run {
            detailTourInclude.run {
                mapObjects = mapview.map.mapObjects.addCollection()

                arrayPlaces.forEachIndexed { index, place ->
                    val pointer = CustomPointer(requireContext())

                    pointer.setValues(place = index + 1, false)

                    val viewProvider = ViewProvider(pointer)
                    mapObjects!!.addPlacemark(place.coordinate, viewProvider)
                }
            }
        }
    }

    private fun submitRequest() {
        val routes =
            arrayPlaces.map { RequestPoint(it.coordinate, RequestPointType.WAYPOINT, null) }

        val drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()

        drivingRouter.requestRoutes(
            routes,
            DrivingOptions(),
            VehicleOptions(),
            object : DrivingSession.DrivingRouteListener {
                override fun onDrivingRoutes(routes: MutableList<DrivingRoute>) {
                    routes.forEach { mapObjects!!.addPolyline(it.geometry) }
                }

                override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {

                }
            }
        )
    }

    override fun onStop() {
        super.onStop()

        binding.run {
            detailTourInclude.run {
                mapview.onStop()
            }
        }
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()

        binding.run {
            detailTourInclude.run {
                mapview.onStart()
            }
        }

        MapKitFactory.getInstance().onStart()
    }
}