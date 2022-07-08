package com.travel.gid.ui.home.child_fragments.gid_fragment.view_model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Gid
import com.travel.gid.domain.usecases.GetGidListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GidViewModel @Inject constructor(
    private val getGidListUseCase: GetGidListUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    ViewModel() {

    private var _gidList = MutableLiveData<Response<Gid>>()
    val gidList: LiveData<Response<Gid>>
        get() = _gidList

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            _error.value = ""
        }

    fun getGidList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _gidList.value = withContext(ioDispatcher) {
                return@withContext getGidListUseCase.getGidList()
            }
        }
    }
}