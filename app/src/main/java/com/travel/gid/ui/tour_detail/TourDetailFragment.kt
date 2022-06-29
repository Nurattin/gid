package com.travel.gid.ui.tour_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.data.models.TourDetailData
import com.travel.gid.databinding.FragmentTourDetailBinding
import com.travel.gid.ui.tour_detail.adapters.IncludedTour
import com.travel.gid.ui.tour_detail.adapters.IncludedTourAdapter
import com.travel.gid.ui.tour_detail.adapters.ToursImageAdapter
import com.travel.gid.utils.CustomPointer
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


@AndroidEntryPoint
class TourDetailFragment : Fragment() {

    private val args by lazy { TourDetailFragmentArgs.fromBundle(requireArguments()) }
    private val viewModel: ToutDetailViewModel by viewModels()
    lateinit var binding: FragmentTourDetailBinding
    private var arrayPlaces = listOf<Places>()
    private var mapObjects: MapObjectCollection? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        exitTransition = MaterialElevationScale(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTourDetail(args.id)

        viewModel.toursDetail.observe(viewLifecycleOwner) {
            binding.containerProgress.visibility = View.GONE
            initBanner(it?.data)
            initDescription(it?.data)

            try {
                arrayPlaces = viewModel.getPlaces()!!
                setupMap(arrayPlaces)
                initMarkPlaces(arrayPlaces)
                submitRequest(arrayPlaces)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Не удалось построить маршрут",
                    Toast.LENGTH_SHORT
                ).show()
                binding.detailTourInclude.mapview.visibility = View.GONE
            }
            binding.detailTourInclude.mapview.setOnClickListener {

                val extras = FragmentNavigatorExtras(binding.detailTourInclude.mapview to "shared_element_container")
                val bundle = bundleOf("places" to arrayPlaces.toTypedArray())
                findNavController().navigate(
                    R.id.action_tourDetailFragment_to_mapFragment,
                    bundle,
                    null,
                    extras
                )
            }
        }
        if (viewModel.toursDetail.value != null) {
            initIncludedTour()
        }
    }

    private fun initBanner(dataTour: TourDetailData?) {
        binding.run {
            dataTour?.let { dataTour ->
                val adapter: ToursImageAdapter =
                    ToursImageAdapter(dataTour.detailPhoto)
                bannerVP.adapter = adapter

                TabLayoutMediator(wormDotsIndicator, bannerVP) { tab, position ->
                }.attach()
            }
        }
    }

    private fun initDescription(dataTour: TourDetailData?) {
        binding.run {
//            priceTour.text =  valueToPrice(dataTour.price)
            dataTour?.let {
                priceTour.text = dataTour.price

                buyTourButton.setOnClickListener {

                }

                detailTourInclude.run {

//                textPrice.text = valueToPrice(dataTour.price)
                    tourPrice.text = dataTour.price
                    txtNameTour.text = viewModel.toursDetail.value!!.data.name
                    tourDuration.text = dataTour.duration
                    tourCount.text = dataTour.peopleCount.toString() + " человек"
                    expandTextView.text = viewModel.toursDetail.value!!.data.description

                    if (expandableText.lineCount < 4) {
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
    }

    private fun initIncludedTour() {
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


    private fun setupMap(arrayPlaces: List<Places>) {
        binding.run {
            detailTourInclude.run {
                mapview.map.isRotateGesturesEnabled = false
                val boundingBox = BoundingBox(
                    Point(arrayPlaces[0].latitude, arrayPlaces[0].longitude),
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

    private val listCustomPointer = ArrayList<CustomPointer>()
    private fun initMarkPlaces(arrayPlaces: List<Places>) {
        binding.run {
            detailTourInclude.run {
                mapObjects = mapview.map.mapObjects.addCollection()

                arrayPlaces.forEachIndexed { index, place ->
                    val pointer = CustomPointer(requireContext())
                    if (listCustomPointer.count() - 1 < index) {
                        listCustomPointer.add(pointer)
                    }
                    pointer.setValues(place = index + 1, false)

                    val viewProvider = ViewProvider(pointer)
                    mapObjects!!.addPlacemark(
                        Point(
                            place.latitude,
                            place.longitude
                        ), viewProvider
                    )
                }
            }
        }
    }

    private fun submitRequest(arrayPlaces: List<Places>) {
        val routes =
            arrayPlaces.map {
                RequestPoint(
                    Point(it.latitude, it.longitude),
                    RequestPointType.WAYPOINT,
                    null
                )
            }

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