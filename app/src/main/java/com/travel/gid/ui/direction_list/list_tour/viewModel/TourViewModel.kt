package com.travel.gid.ui.direction_list.list_tour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Price
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.domain.usecases.GetHomeUseCase
import com.travel.gid.domain.usecases.GetTourListFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val getTourListFilter: GetTourListFilter
) : ViewModel() {

    private val _priceRange = MutableLiveData<Price>()
    val priceRange: LiveData<Price>
        get() = _priceRange

    private val _filters = MutableLiveData<Response<Filters>>()
    val filters: LiveData<Response<Filters>>
        get() = _filters

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>>
        get() = _categories

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _tours = MutableLiveData<Response<Tour>>()
    val tours: LiveData<Response<Tour>>
        get() = _tours

    init {

        viewModelScope.launch {
            _filters.value = getFilterUseCase.getFilterParams()
            val listCategories = filters.value?.body()?.data?.listCategories
            listCategories?.add(0, Categories(0, "Все", true))
            _categories.value = listCategories!!
        }
        viewModelScope.launch {
            _tours.value = getHomeUseCase.getTours()
        }


    }

    fun changeCategories(pos: Int) {
        _categories.value!![pos].enable = false

    }

    fun changePos(pos: Int) {
        _categoriesPos = pos
    }

    fun getAllTour() {
        viewModelScope.launch {
            _tours.value = getHomeUseCase.getTours()
        }
    }

    fun getTourByCategories(listId: Array<Int>) {
        Log.i("dwa", "$listId")
        viewModelScope.launch {
            _tours.value = getTourListFilter.getTourListFilter(categories = listId)
        }
    }
}