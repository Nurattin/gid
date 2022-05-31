package com.travel.gid.ui.home.child_fragments.event_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class EventViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase): ViewModel() {

    private val _event = MutableLiveData<Response<Direction>>()
    val event: LiveData<Response<Direction>>
        get() = _event

    init {
        viewModelScope.launch {
            _event.value = getHomeUseCase.getDirection()
        }
    }
}