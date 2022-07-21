package com.travel.gid.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Categories
import com.travel.gid.data.models.FilterParamsTour
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

    var filters = MutableLiveData<FilterParamsTour>()


    var priceFrom: Int? = null

    var priceTo: Int? = null
}