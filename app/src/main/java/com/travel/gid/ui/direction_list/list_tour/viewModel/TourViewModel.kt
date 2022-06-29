package com.travel.gid.ui.direction_list.list_tour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.domain.usecases.GetHomeUseCase
import com.travel.gid.domain.usecases.GetTourListFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val getTourListFilter: GetTourListFilter,
) : ViewModel() {

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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            _error.value = ""
            Log.i("dwa", "call")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.value = getFilterUseCase.getFilterParams()
            val listCategories = filters.value?.body()?.data?.listCategories
            _categories.value = listCategories!!
        }
        getAllTour(null)

    }

    fun changeCategories(pos: Int) {
        _categories.value!![pos].enable = false

    }

    fun changePos(pos: Int) {
        _categoriesPos = pos
    }

    fun getAllTour(filter: HashMap<String, Any>?) {
        viewModelScope.launch(coroutineExceptionHandler) {
            if (filter != null) {
                _tours.value =
                    getTourListFilter.getTourListFilter(
                        categories = filter["categories"] as List<Int>?,
                        priceFrom = filter["startPrice"] as Int,
                        priceTo = filter["endPrice"] as Int
                    )
            } else {
                _tours.value = getHomeUseCase.getTours()
            }
        }
    }

    fun getTourByCategories(listId: List<Int>) {
        Log.i("dwa", "$listId")
        viewModelScope.launch(coroutineExceptionHandler) {
            _tours.value = getTourListFilter.getTourListFilter(categories = listId)
        }
    }
}