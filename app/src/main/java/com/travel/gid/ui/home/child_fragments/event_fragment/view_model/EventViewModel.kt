package com.travel.gid.ui.home.child_fragments.event_fragment.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.usecases.GetHomeUseCase
import com.travel.gid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class EventViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _event = MutableLiveData<Response<Direction>>()
    val event: LiveData<Response<Direction>>
        get() = _event

    init {
        getEvent()
    }

    fun getEvent() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _event.value = withContext(ioDispatcher) {
                return@withContext getHomeUseCase.getDirection()
            }
        }
    }
}