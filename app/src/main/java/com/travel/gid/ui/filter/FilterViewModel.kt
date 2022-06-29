package com.travel.gid.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Price
import com.travel.gid.domain.usecases.GetFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
) : ViewModel() {

    private val _filters = MutableLiveData<Response<Filters>>()
    val filters: LiveData<Response<Filters>>
        get() = _filters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
        }

    init {
        getFilter()
    }

    fun getFilter() {
        viewModelScope.launch(coroutineExceptionHandler) {
            Log.i("filter", "Call")
            _filters.value = getFilterUseCase.getFilterParams()
        }
    }

    fun getCategories(): List<Categories>? = filters.value?.body()?.data?.listCategories


    fun getPriceRange(): Price? {
        return filters.value?.body()?.data?.price
    }

    override fun onCleared() {
        super.onCleared()
    }

}