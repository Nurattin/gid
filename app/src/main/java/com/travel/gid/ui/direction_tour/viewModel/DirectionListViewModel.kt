package com.travel.gid.ui.direction_tour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DirectionListViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val _filters = MutableLiveData<List<Categories>>()
    val filters: LiveData<List<Categories>>
        get() = _filters

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _directionList = MutableLiveData<Response<Direction>>()
    val directionList: LiveData<Response<Direction>>
        get() = _directionList

    init {
        viewModelScope.launch {
            val listCategories = getFilterUseCase.getFilterParams()?.body()?.data?.listCategories
            listCategories?.add(0, Categories(0, "Все", true))
            _filters.value = listCategories!!
        }
        viewModelScope.launch {
            _directionList.value = getHomeUseCase.getDirection()
        }
    }
    fun changeCategories(pos: Int){
        _filters.value!![pos].enable = false
    }
    fun changePos(pos: Int){
        _categoriesPos = pos
    }

}