package com.travel.gid.ui.tour_list.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.FilterParamsTour
import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Tour
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asSuccess
import com.travel.gid.domain.usecases.GetFilterUseCase
import com.travel.gid.domain.usecases.GetTourListUseCase
import com.travel.gid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
    private val getTourListUseCase: GetTourListUseCase,
) : BaseViewModel() {

    var  startPost = 0

    private val _filters = MutableLiveData<Result<Filters>>()
    val filters: LiveData<Result<Filters>>
        get() = _filters

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>>
        get() = _categories

    private var _categoriesPos = 0
    val categoriesPos: Int
        get() = _categoriesPos

    private val _tours = MutableLiveData<Result<Tour>>()
    val tours: LiveData<Result<Tour>>
        get() = _tours


    private fun getFilterParams() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.value = getFilterUseCase.invoke()
        }
    }

    fun getListCategories() {
        filters.value?.asSuccess()?.value?.data?.let {
            val listCategories = it.listCategories
            listCategories.add(0, Categories(0, "Все", true))
            _categories.value = listCategories
        }
    }

    init {
        getFilterParams()
        getAllTour()

    }

    fun changeCategories(posCategories: Int, pos: Int) {
        if (posCategories != pos) {
            _categories.value!![posCategories].enable = false
            _categoriesPos = pos
        }

    }

    fun getAllTour(filter: FilterParamsTour? = null) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _tours.value = getTourListUseCase.invoke(
                categories = filter?.categoriesAccept,
                priceFrom = filter?.startPrice,
                priceTo = filter?.endPrice,
                orderByPrice = filter?.sortedParams,
            )
        }

    }
}
