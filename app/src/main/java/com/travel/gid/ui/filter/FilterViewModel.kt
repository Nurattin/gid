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

    init {
        viewModelScope.launch {
            _filters.value = getFilterUseCase.getFilterParams()
        }
    }

    fun getCategories(): List<Categories> {
        val listCategories = filters.value?.body()?.data?.listCategories
        return if (listCategories!![0].name != "Все") {
            listCategories.add(0, Categories(0, "Все", true))
            listCategories
        } else listCategories

    }

    fun getPriceRange(): Price? {
        return filters.value?.body()?.data?.price
    }

    override fun onCleared() {
        Log.i("dwa", "destroy")
        super.onCleared()
    }
}