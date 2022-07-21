package com.travel.gid.ui.home.child_fragments.direction_fragment.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetHomeUseCase
import com.travel.gid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DirectionViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val ioDispatcher: CoroutineDispatcher
) :
    BaseViewModel() {

    private val _directionsList = MutableLiveData<Response<Direction>>()
    val directionsList: LiveData<Response<Direction>> get() = _directionsList

    private val _tourList = MutableLiveData<Response<Tour>>()
    val tourList: LiveData<Response<Tour>> get() = _tourList


    init{
        getTours()
        getDirectionList()
    }

    fun getTours() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _tourList.value = withContext(ioDispatcher){
                return@withContext getHomeUseCase.getTours()
            }
        }
    }

    fun getDirectionList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _directionsList.value = withContext(ioDispatcher){
                return@withContext getHomeUseCase.getDirection()
            }
        }
    }
}