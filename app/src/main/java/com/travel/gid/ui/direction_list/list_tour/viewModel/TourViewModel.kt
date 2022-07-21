package com.travel.gid.ui.direction_list.list_tour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.FilterParamsTour
import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.domain.usecases.GetTourListUseCase
import com.travel.gid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
    private val getTourListUseCase: GetTourListUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : BaseViewModel() {

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


    private fun getFilterParams() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.value = getFilterUseCase.getFilterParams()
        }
    }

    fun getListCategories() {
        val listCategories = filters.value?.body()?.data?.listCategories
        listCategories?.add(0, Categories(0, "Все", true))
        _categories.value = listCategories!!
    }

    init {
        getFilterParams()
        getAllTour(FilterParamsTour())

    }

    fun changeCategories(posCategories: Int, pos: Int) {
        if (posCategories != pos) {
            _categories.value!![posCategories].enable = false
            _categoriesPos = pos
        }

    }

    fun refreshData(filter: FilterParamsTour) {
        getAllTour(filter)
        getFilterParams()
    }

    fun getAllTour(filter: FilterParamsTour) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _tours.value = withContext(ioDispatcher) {
                return@withContext getTourListUseCase.getTourListFilter(
                    categories = filter.categoriesAccept,
                    priceFrom = filter.startPrice,
                    priceTo = filter.endPrice,
                    orderByPrice = filter.sortedParams,
                )
            }

        }
    }
}