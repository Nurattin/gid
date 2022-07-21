package com.travel.gid.ui.home.child_fragments.gid_fragment.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Gid
import com.travel.gid.domain.usecases.GetGidListUseCase
import com.travel.gid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GidViewModel @Inject constructor(
    private val getGidListUseCase: GetGidListUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    BaseViewModel() {

    private var _gidList = MutableLiveData<Response<Gid>>()
    val gidList: LiveData<Response<Gid>>
        get() = _gidList

    init{
        getGidList()
    }

    fun getGidList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _gidList.value = withContext(ioDispatcher) {
                return@withContext getGidListUseCase.getGidList()
            }
        }
    }
}