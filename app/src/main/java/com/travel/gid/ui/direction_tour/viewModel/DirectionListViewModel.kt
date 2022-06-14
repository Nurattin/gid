package com.travel.gid.ui.direction_tour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DirectionListViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val _categories = MutableLiveData<Categories>()
    val categories: LiveData<Categories>
        get() = _categories

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _directionList = MutableLiveData<Response<Direction>>()
    val directionList: LiveData<Response<Direction>>
        get() = _directionList

    init {
        viewModelScope.launch {
            _categories.value = getHomeUseCase.getCategories()
        }
        viewModelScope.launch {
            _directionList.value = getHomeUseCase.getDirection()
        }
    }
    fun changeCategories(pos: Int){
        _categories.value!!.data[pos].enable = false
    }
    fun changePos(pos: Int){
        _categoriesPos = pos
    }

}