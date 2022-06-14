package com.travel.gid.ui.direction_list.list_tour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetHomeUseCase
import com.travel.gid.domain.usecases.GetTourByCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getTourByCategories: GetTourByCategories
) : ViewModel() {

    private val _categories = MutableLiveData<Categories>()
    val categories: LiveData<Categories>
        get() = _categories

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _tours = MutableLiveData<Response<Tour>>()
    val tours: LiveData<Response<Tour>>
        get() = _tours

    init {
        viewModelScope.launch {
            _categories.value = getHomeUseCase.getCategories()
        }
        viewModelScope.launch {
            _tours.value = getHomeUseCase.getTours()
        }
    }

    fun changeCategories(pos: Int) {
        _categories.value!!.data[pos].enable = false
    }

    fun changePos(pos: Int) {
        _categoriesPos = pos
    }

    fun getTourByCategories(id: Long) {
        when (id) {
            0L -> viewModelScope.launch {
                _tours.value = getHomeUseCase.getTours()
            }
            else -> {
                viewModelScope.launch {
                    _tours.value = getTourByCategories.getToursByCategories(id)
                    Log.i("dwa", _tours.value.toString())
                }
            }
        }
    }
}