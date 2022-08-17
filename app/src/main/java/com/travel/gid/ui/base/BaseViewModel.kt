package com.travel.gid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travel.gid.utils.BaseExceptionMapper
import com.travel.gid.utils.Response
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel: ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        response.value = Response.Error
        swipeProgress.value
        error.value = BaseExceptionMapper.map(throwable)
    }

    var error = MutableLiveData<String>()

    var progressBar = MutableLiveData(false)

    var swipeProgress = MutableLiveData(false)

    var response = MutableLiveData<Response>()

}