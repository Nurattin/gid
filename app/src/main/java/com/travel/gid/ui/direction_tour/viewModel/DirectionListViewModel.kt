package com.travel.gid.ui.direction_tour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.usecases.GetDirectionListUseCase
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.ui.base.BaseViewModel
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectionListViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
    private val getDirectionListUseCase: GetDirectionListUseCase
) : BaseViewModel() {

    private val _filters = MutableLiveData<List<Categories>>()
    val filters: LiveData<List<Categories>>
        get() = _filters

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _directionList = MutableLiveData<Result<Direction>>()
    val directionList: LiveData<Result<Direction>>
        get() = _directionList

    init {
        viewModelScope.launch {
            val listCategories = getFilterUseCase().asSuccess().value.data.listCategories
            listCategories.add(0, Categories(0, "Все", true))
            _filters.value = listCategories
        }
        viewModelScope.launch {
            _directionList.value = getDirectionListUseCase.invoke()
        }
    }

    fun changeCategories(pos: Int) {
        _filters.value!![pos].enable = false
    }

    fun changePos(pos: Int) {
        _categoriesPos = pos
    }

}