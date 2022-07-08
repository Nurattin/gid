package com.travel.gid.ui.map_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.databinding.FragmentMapBinding
import com.travel.gid.ui.map_detail.SnapOnScrollListener.OnSnapPositionChangeListener
import com.travel.gid.ui.map_detail.SnapOnScrollListener.SnapOnScrollListener
import com.travel.gid.ui.map_detail.SnapOnScrollListener.attachSnapHelperWithListener
import com.travel.gid.ui.map_detail.adapter.MapAdapter
import com.travel.gid.utils.CustomPointer
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.*
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.Error
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.concurrent.schedule


@AndroidEntryPoint
class MapFragment : Fragment(), DrivingSession.DrivingRouteListener {

    private val listPointer = HashMap<Int, PlacemarkMapObject>()
    private var lastPosition = 1
    private var routes = listOf<RequestPoint>()
    private val args: MapFragmentArgs by navArgs()
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession: DrivingSession? = null
    private val listCustomPointer = ArrayList<CustomPointer>()
    private var arrayPointer = hashMapOf<Int, Point>()
    private var arrayPlaces = arrayOf<Places>()
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapObjects: MapObjectCollection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        mapObjects = binding.mapview.map.mapObjects.addCollection();
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arrayPlaces = args.places
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupMap()
        submitRequest()
        initMarkPlaces()

        val snapHelper = PagerSnapHelper()
        val adapter = MapAdapter()

        adapter.data = arrayPlaces.toList()
        binding.detailPlaceRecycler.adapter = adapter
        moveMapByPosition(0)
        binding.detailPlaceRecycler.attachSnapHelperWithListener(
            snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, object :
                OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    changePointer(position + 1)
                }
            })
        binding.mapview.map.mapObjects.addTapListener { mapObject, point ->
            moveMapByPointer(point)
            true
        }
        stopProgressBar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun moveMapByPointer(point: Point) {
        var cameraPosition = binding.mapview.map.cameraPosition(
            getBoundingBoxThenCameraMove(
                point.latitude,
                point.longitude,
                5
            )
        )
        cameraPosition = CameraPosition(
            point,
            cameraPosition.zoom - 1,
            cameraPosition.azimuth,
            cameraPosition.tilt
        )
        binding.mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 1f), null)
    }

    private fun moveMapByPosition(pos: Int) {
        val boundingBox = getBoundingBoxThenCameraMove(
            arrayPlaces[pos].geo.lat,
            arrayPlaces[pos].geo.lng,
            5
        )
        var cameraPosition = binding.mapview.map.cameraPosition(boundingBox)
        cameraPosition = CameraPosition(
            Point(arrayPlaces[pos].geo.lat, arrayPlaces[pos].geo.lng),
            cameraPosition.zoom - 1,
            cameraPosition.azimuth,
            cameraPosition.tilt
        )
        binding.mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 1f), null)
    }

    private fun changePointer(pos: Int) {
        val lastPointer = CustomPointer(requireContext())
        lastPointer.setValues(lastPosition, false)
        listPointer[lastPosition]?.setView(ViewProvider(lastPointer))
        lastPosition = pos
        val pointer = CustomPointer(requireContext())
        pointer.setValues(pos, true)
        listPointer[pos]?.setView(ViewProvider(pointer))
        moveMapByPosition(pos - 1)
    }

    private fun setupMap() {
        binding.run {
            var cameraPosition = mapview.map.cameraPosition(getBoundingBox())
            cameraPosition = CameraPosition(
                cameraPosition.target,
                cameraPosition.zoom - 1,
                cameraPosition.azimuth,
                cameraPosition.tilt
            )
            mapview.map.move(cameraPosition)
        }
    }

    private fun initMarkPlaces() {
        binding.run {
            arrayPlaces.forEachIndexed { index, place ->
                arrayPointer[index + 1] =
                    (Point(place.geo.lat, place.geo.lng))
                val pointer = CustomPointer(requireContext())
                mapObjects.userData = index
                if (listCustomPointer.count() - 1 < index) {
                    listCustomPointer.add(pointer)
                }
                pointer.setValues(place = index + 1, index + 1 == lastPosition)
                val viewProvider = ViewProvider(pointer)
                listPointer[index + 1] =
                    mapObjects.addPlacemark(
                        Point(
                            place.geo.lat,
                            place.geo.lng
                        ), viewProvider
                    )
            }
        }
    }

    private fun submitRequest() {
        routes = arrayPlaces.map {
            RequestPoint(
                Point(it.geo.lat, it.geo.lng),
                RequestPointType.WAYPOINT,
                null
            )
        }
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        drivingSession = drivingRouter?.requestRoutes(routes, drivingOptions, vehicleOptions, this);
    }


    private fun getBoundingBoxThenCameraMove(lat: Double, lng: Double, zoom: Int = 1): BoundingBox {
        val north = lat + 0.1 * zoom
        val east = lng - 0.1 * zoom
        val west = lng + 0.1 * zoom
        val south = lat - 0.1 * zoom
        return BoundingBox(Point(north, east), Point(south, west))
    }

    private fun getBoundingBox(): BoundingBox {
        var lat: Double
        var lng: Double
        var north = arrayPlaces[0].geo.lat // север
        var west = arrayPlaces[0].geo.lng // запад
        var east = arrayPlaces[0].geo.lng // восток
        var south = arrayPlaces[0].geo.lat // юг
        arrayPlaces.forEach {
            lat = it.geo.lat
            lng = it.geo.lng
            north = if (north < lat) lat else north
            west = if (west < lng) lng else west
            east = if (east > lng) lng else east
            south = if (south > lat) lat else south
        }
        return BoundingBox(Point(north, east), Point(south, west))
    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        p0.forEach { mapObjects.addPolyline(it.geometry) }
    }

    override fun onDrivingRoutesError(p0: Error) {
        Toast.makeText(requireContext(), p0.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        binding.run {
            mapview.onStop()
        }
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        binding.run {
            mapview.onStart()
        }
    }

    private fun stopProgressBar() {
        Timer().schedule(500) {
            activity?.runOnUiThread {
                with(binding) {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}
