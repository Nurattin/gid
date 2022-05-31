package com.travel.gid.ui.direction_list.tour.viewModel

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
class TourViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>>
        get() = _categories

    private val _tours = MutableLiveData<Response<Direction>>()
    val tours: LiveData<Response<Direction>>
        get() = _tours

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _categories.value = listOf(
            Categories("Популярные"),
            Categories("Исторические"),
            Categories("Кафе"),
            Categories("Природа")
        )
        viewModelScope.launch {
            _tours.value = getHomeUseCase.getDirection()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}