package com.travel.gid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travel.gid.utils.BaseExceptionMapper
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel: ViewModel() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        error.value = BaseExceptionMapper.map(throwable)
    }
    val error = MutableLiveData<String>()

}