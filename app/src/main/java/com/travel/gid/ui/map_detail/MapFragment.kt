package com.travel.gid.ui.map_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.transition.MaterialContainerTransform
import com.travel.gid.R
import com.travel.gid.data.models.Places
import com.travel.gid.databinding.FragmentMapBinding
import com.travel.gid.ui.map_detail.SnapOnScrollListener.OnSnapPositionChangeListener
import com.travel.gid.ui.map_detail.SnapOnScrollListener.SnapOnScrollListener
import com.travel.gid.ui.map_detail.SnapOnScrollListener.attachSnapHelperWithListener
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
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val listPointer = HashMap<Int, PlacemarkMapObject>()
    private var lastPosition = 1
    private var listImage = mutableListOf<String>()
    private val args: MapFragmentArgs by navArgs()
    private val viewModel: MapViewModel by viewModels()
    private val listCustomPointer = ArrayList<CustomPointer>()
    private var center = Point(0.0, 0.0)
    private var arrayPointer = hashMapOf<Int, Point>()
    private var arrayPlaces = arrayOf<Places>()
    lateinit var binding: FragmentMapBinding
    var mapObjects: MapObjectCollection? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = MaterialContainerTransform()
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arrayPlaces = args.places
        center = getCenter(arrayPlaces)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupMap(arrayPlaces)
        initMarkPlaces(arrayPlaces)
        submitRequest(arrayPlaces)

        val snapHelper = PagerSnapHelper()
        val adapter = MapAdapter()
        adapter.data = arrayPlaces.toList()
        binding.detailPlaceRecycler.adapter = adapter


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
            //            binding.detailPlaceRecycler.scrollToPosition(position)

            true
        }
        stopProgressBar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun stopProgressBar() {
        binding.apply {
            progressBar.visibility = View.GONE
            mapview.visibility = View.VISIBLE
        }
    }

    private fun getCenter(arrayPlaces: Array<Places>): Point {
        var latitude = 0.0
        var longitude = 0.0
        val count = arrayPlaces.size
        arrayPlaces.forEach {
            latitude += it.latitude
            longitude += it.longitude
        }
        return Point(latitude / count, longitude / count)
    }

    private fun setupMap(arrayPlaces: Array<Places>) {
        binding.run {
            mapview.map.isRotateGesturesEnabled = true
            val boundingBox = BoundingBox(
                Point(43.740940, 46.728409),
                Point(41.672912, 48.666751)
            )
            var cameraPosition = mapview.map.cameraPosition(boundingBox)
            cameraPosition = CameraPosition(
                cameraPosition.target,
                cameraPosition.zoom,
                cameraPosition.azimuth,
                cameraPosition.tilt
            )
            mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 0f), null)
        }
    }

    private fun moveMapByPointer(point: Point) {
        val boundingBox = BoundingBox(point, center)
        var cameraPosition = binding.mapview.map.cameraPosition(boundingBox)
        cameraPosition = CameraPosition(
            point,
            cameraPosition.zoom - 1,
            cameraPosition.azimuth,
            cameraPosition.tilt
        )
        binding.mapview.map.move(cameraPosition, Animation(Animation.Type.SMOOTH, 1f), null)
    }


    private fun moveMapByPosition(pos: Int) {
        val boundingBox =
            BoundingBox(Point(arrayPlaces[pos].latitude, arrayPlaces[pos].longitude), center)
        var cameraPosition = binding.mapview.map.cameraPosition(boundingBox)
        cameraPosition = CameraPosition(
            Point(arrayPlaces[pos].latitude, arrayPlaces[pos].longitude),
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


    private fun initMarkPlaces(arrayPlaces: Array<Places>) {
        binding.run {
            mapObjects = mapview.map.mapObjects.addCollection()
            arrayPlaces.forEachIndexed { index, place ->
                arrayPointer[index + 1] = (Point(place.latitude, place.longitude))
                val pointer = CustomPointer(requireContext())
                mapObjects!!.userData = index
                if (listCustomPointer.count() - 1 < index) {
                    listCustomPointer.add(pointer)
                }

                pointer.setValues(place = index + 1, index + 1 == lastPosition)
                val viewProvider = ViewProvider(pointer)
                listPointer[index + 1] =
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
                    Toast.makeText(
                        requireContext(),
                        "Не удалось построить маршрут пожалуйсе повторите снова",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    fun getImageByPosition() {
        listImage = mutableListOf()
        arrayPlaces.forEach {
            listImage.add(it.image)
        }
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
