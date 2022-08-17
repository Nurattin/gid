package com.travel.gid.ui.home.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.R
import com.travel.gid.data.models.*
import com.travel.gid.domain.usecases.GetDirectionListUseCase
import com.travel.gid.domain.usecases.GetEventListUseCase
import com.travel.gid.domain.usecases.GetGidListUseCase
import com.travel.gid.ui.base.BaseViewModel
import com.travel.gid.data.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
    private val getDirectionListUseCase: GetDirectionListUseCase,
    private val getGidListUseCase: GetGidListUseCase,
) : BaseViewModel() {

    /**
     *
     * the code below refers to child HomeFragment
     */

    var listImageBanner = MutableLiveData(
        listOf(
            "https://img.freepik.com/free-vector/welcome-egypt-online-journey-planning-booking-isometric-website-horizontal-banner-with-pyramids-palms-travelers_1284-32154.jpg?t=st=1655737200~exp=1655737800~hmac=74351c4b069ce9adff328ebf277170bafb35edca3ed6ad183edd7cd3b041fa63&w=1800",
            "https://generisonline.com/wp-content/uploads/2022/05/GettyImages-187613060-2c4df95dc90045d484a01dfe21880c40.jpg",
            "https://s28943.pcdn.co/wp-content/uploads/2018/04/Tour-Cart-Hero.jpg",
            "https://www.peruhop.com/wp-content/uploads/IMG_20180718_082918-2.jpg",
        )
    )

    private var _listCategoriesHome =
        MutableLiveData(
            listOf(
                CategoriesHome(
                    id = 0,
                    iconChecked = R.drawable.ic_globe,
                    iconUnChecked = R.drawable.ic_globe_uncheck,
                    name = "Интересное",
                    isChecked = true
                ),
                CategoriesHome(
                    id = 1,
                    iconChecked = R.drawable.ic_gid_check,
                    iconUnChecked = R.drawable.ic_gid_uncheck,
                    name = "Гиды"
                ),
                CategoriesHome(
                    id = 2,
                    iconChecked = R.drawable.ic_place_check,
                    iconUnChecked = R.drawable.ic_place_uncheck,
                    name = "События"
                ),
                CategoriesHome(
                    id = 3,
                    iconChecked = R.drawable.ic_bascket_check,
                    iconUnChecked = R.drawable.ic_bascket_uncheck,
                    name = "Еда"
                ),
            )
        )

    val listCategoriesHome: LiveData<List<CategoriesHome>> = _listCategoriesHome

    fun changePosCategories(lastPos: Int, newPos: Int){
        _listCategoriesHome.value?.let {
            val newList = ArrayList(it)

            val lastItem = it[lastPos].copy(isChecked = false)
            val newItem = it[newPos].copy(isChecked = true)

            newList[lastPos] = lastItem
            newList[newPos] = newItem

            currentItem = newPos
            _listCategoriesHome.value = newList
        }
    }

    var currentItem = 0


    /**
     *
     * the code below refers to child DirectionsFragment
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
     * the code below refers to child GidFragment
     */


    private var _gidList = MutableLiveData<Result<Gid>>()
    val gidList: LiveData<Result<Gid>>
        get() = _gidList


    fun getGidList() {
        viewModelScope.launch {
            _gidList.value = getGidListUseCase.invoke()
        }
    }

    /**
     *
     * the code below refers to child EventFragment
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