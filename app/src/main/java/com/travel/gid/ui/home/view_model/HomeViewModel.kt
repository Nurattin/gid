package com.travel.gid.ui.home.view_model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Hiking
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Events
import com.travel.gid.data.models.Gid
import com.travel.gid.data.models.Tour
import com.travel.gid.data.result.Result
import com.travel.gid.domain.usecases.GetDirectionListUseCase
import com.travel.gid.domain.usecases.GetEventListUseCase
import com.travel.gid.domain.usecases.GetGidListUseCase
import com.travel.gid.ui.base.BaseViewModel
import com.travel.gid.ui.home.compose.back_layer.Categories
import com.travel.gid.ui.home.compose.back_layer.HomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
    private val getDirectionListUseCase: GetDirectionListUseCase,
    private val getGidListUseCase: GetGidListUseCase,
) : BaseViewModel() {

    private var getGidListJob = Job()
    private var getDirectionListJob = Job()
    private var getTourListJob = Job()

    /**
     *
     * the code below refers to child HomeFragment
     */

    var listImageBanner =
        listOf(
            "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
            "https://generisonline.com/wp-content/uploads/2022/05/GettyImages-187613060-2c4df95dc90045d484a01dfe21880c40.jpg",
            "https://s28943.pcdn.co/wp-content/uploads/2018/04/Tour-Cart-Hero.jpg",
            "https://www.peruhop.com/wp-content/uploads/IMG_20180718_082918-2.jpg",
        )

    val currentScreenPosition: MutableState<HomeScreen>
        get() =
            when (currentPosition.value) {
                0 -> mutableStateOf(HomeScreen.TourScreen)
                1 -> mutableStateOf(HomeScreen.GidScreen)
                2 -> mutableStateOf(HomeScreen.PlaceScreen)
                3 -> mutableStateOf(HomeScreen.HotelScreen)
                else -> mutableStateOf(HomeScreen.TourScreen)
            }


    private var currentPosition = mutableStateOf(0)

    val listCategories = listOf(


        Categories(
            icons = Icons.Rounded.Map,
            name = "Туры",
            isChecked = mutableStateOf(true)
        ),
        Categories(
            icons = Icons.Rounded.Hiking,
            name = "Гиды",
            isChecked = mutableStateOf(false)
        ),
        Categories(
            icons = Icons.Rounded.Place,
            name = "Места",
            isChecked = mutableStateOf(false)
        ),
        Categories(
            icons = Icons.Rounded.Hotel,
            name = "Отели",
            isChecked = mutableStateOf(false)
        ),
    )

    fun changeCategoriesScreen(newPosition: Int) {
        listCategories.let {
            if (currentPosition.value != newPosition) {
                it[newPosition].isChecked.value = true
                it[currentPosition.value].isChecked.value = false
                cancelLastJobAndCreateNewJob(
                    lastScreenPos = currentPosition.value,
                    newScreenPos = newPosition,
                )
                currentPosition.value = newPosition
            }
        }
    }

    fun refreshData(){
        when(currentScreenPosition.value){
            HomeScreen.TourScreen -> {
//                getDirectionList()
            }
            HomeScreen.GidScreen -> {
                getGidList()
            }
            HomeScreen.PlaceScreen -> TODO()
            HomeScreen.HotelScreen -> TODO()
        }
    }

    private fun cancelLastJobAndCreateNewJob(lastScreenPos: Int, newScreenPos: Int) {
        cancelLastJob(lastScreenPos)
        createNewJob(newScreenPos)
    }

    private fun createNewJob(newScreenPos: Int) {
        when (newScreenPos) {

            0 -> {
                getDirectionListJob = Job()
                getTourListJob = Job()
            }

            1 -> {
                getGidListJob = Job()
            }

        }
    }

    private fun cancelLastJob(lastScreenPos: Int) {
        when (lastScreenPos) {
            0 -> {
                getDirectionListJob.cancel()
                getTourListJob.cancel()
            }
            1 -> {
                getGidListJob.cancel()
            }
        }
    }


    /**
     *
     * the code below refers to child DirectionsScreen
     */

    private val _directionsList = MutableLiveData<Result<Direction>>()
    val directionsList: LiveData<Result<Direction>> get() = _directionsList

    private val _tourList = MutableLiveData<Response<Tour>>()
    val tourList: LiveData<Response<Tour>> get() = _tourList

    fun getDirectionList() {
        viewModelScope.launch {
            _directionsList.value = getDirectionListUseCase.invoke()
        }
    }

    /**
     *
     * the code below refers to child GidScreen
     *
     */


    private var _gidList = mutableStateOf<Result<Gid>>(Result.Loading())
    val gidList: MutableState<Result<Gid>>
        get() = _gidList


    fun getGidList() {
        viewModelScope.launch(getGidListJob) {
            gidList.value = Result.Loading()
            _gidList.value = getGidListUseCase.invoke()
        }
    }

    /**
     *
     * the code below refers to child EventScreen
     */

    private val _events = MutableLiveData<Result<Events>>()
    val events: LiveData<Result<Events>>
        get() = _events


    fun getEvent() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _events.value = getEventListUseCase.invoke()
        }
    }
}