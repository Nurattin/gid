package com.travel.gid.ui.home.compose.back_layer

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.travel.gid.ui.home.view_model.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeBackLayerContent(
    modifierBanner: Modifier = Modifier,
    modifierCategories: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onRefresh: () -> Unit,
    onClickChangeCategories: (Int) -> Unit
) {
    val isRefresh = remember {
        mutableStateOf(false)
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefresh.value)
    val coroutineScope = rememberCoroutineScope()


    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        coroutineScope.launch {
            isRefresh.value = true
            delay(1000)
            isRefresh.value = false
        }
        onRefresh()
    }) {
        LazyColumn {
            item {
                LazyListBanner(
                    modifier = modifierBanner,
                    listItem = viewModel.listImageBanner
                )
            }
            item {
                LazyListCategories(
                    modifier = modifierCategories,
                    listCategories = viewModel.listCategories,
                    onClickChangeCategories = { newPosition ->
                        onClickChangeCategories(newPosition)
                    })
            }
        }
    }
}