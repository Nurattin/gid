package com.travel.gid.ui.map_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.transition.MaterialContainerTransform
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.databinding.FragmentMapBinding
import com.travel.gid.ui.map_detail.adapter.MapAdapter
import com.travel.gid.ui.map_detail.viewModel.MapViewModel
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
class MapFragment : Fragment() {

    private val listPointer = HashMap<Int, CustomPointer>()
    private val args: MapFragmentArgs by navArgs()
    private val viewModel: MapViewModel by viewModels()
    private val listCustomPointer = ArrayList<CustomPointer>()
    lateinit var binding: FragmentMapBinding
    var mapObjects: MapObjectCollection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        sharedElementEnterTransition = MaterialContainerTransform()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val arrayPlace = args.places

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupMap(arrayPlace)
        initMarkPlaces(arrayPlace)
        submitRequest(arrayPlace)

        val snapHelper = PagerSnapHelper()


        val adapter = MapAdapter()
        adapter.data = arrayPlace.toList()
        binding.detailPlaceRecycler.adapter = adapter
        snapHelper.attachToRecyclerView(binding.detailPlaceRecycler)

        changePointer(1)

        binding.mapview.map.mapObjects.addTapListener { mapObject, point ->
            true
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun setupMap(arrayPlaces: Array<Places>) {
        binding.run {

            mapview.map.isRotateGesturesEnabled = true
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
            mapview.setNoninteractive(false)
        }

    }

    private fun changePointer(pos: Int) {
        listPointer[pos]?.setValues(pos, true)
    }


    private fun initMarkPlaces(arrayPlaces: Array<Places>) {
        binding.run {
            mapObjects = mapview.map.mapObjects.addCollection()
            arrayPlaces.forEachIndexed { index, place ->
                val pointer = CustomPointer(requireContext())
                if (listCustomPointer.count() - 1 < index) {
                    listCustomPointer.add(pointer)
                }
                pointer.setValues(place = index + 1, false)
                listPointer[index] = pointer
                val viewProvider = ViewProvider(pointer)
                mapObjects!!.addPlacemark(Point(place.latitude, place.longitude), viewProvider)
            }
        }


    }

    private fun submitRequest(arrayPlaces: Array<Places>) {
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
            mapview.onStop()
        }
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        binding.run {
            mapview.onStart()
        }
        MapKitFactory.getInstance().onStart()
    }

}
