package com.travel.gid.ui.home.compose.front_layer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.travel.gid.R
import com.travel.gid.data.models.Direction
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.ui.home.view_model.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable

fun TourScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onClickShowAllTour: () -> Unit
) {

    val directionList by viewModel.directionsList.observeAsState()
    val coroutine = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton = remember { derivedStateOf { listState.firstVisibleItemIndex > 3 } }

    Box(modifier = Modifier) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item { DirectionText() }
            item { LazyListDirection(directionList) }
            stickyHeader { TourText(onClickShowAllTour) }
            items(count = 10) { TourItem() }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            visible = showButton.value,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            ButtonExpandLess(coroutine, listState)
        }
    }
}

@Composable
private fun ButtonExpandLess(
    coroutine: CoroutineScope,
    listState: LazyListState
) {
    FloatingActionButton(
        backgroundColor = Color.White,
        modifier = Modifier
            .alpha(0.8f)
            .size(40.dp),
        onClick = {
            coroutine.launch {
                listState.animateScrollToItem(index = 2)
            }
        }) {
        Icon(
            modifier = Modifier.size(30.dp),
            tint = Color.Gray,
            imageVector = Icons.Rounded.ExpandLess,
            contentDescription = null
        )
    }
}

@Composable
private fun TourText(onClickShowAllTour: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            text = "Туры"
        )

        ClickableText(
            style = MaterialTheme.typography.body1,
            text = AnnotatedString("Все туры"),
            onClick = { onClickShowAllTour() })
    }
}

@Composable
private fun DirectionText() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            text = "Направления"
        )

        ClickableText(
            style = MaterialTheme.typography.body1,
            text = AnnotatedString("Все направления"),
            onClick = {})
    }
}

@Composable
private fun LazyListDirection(directionList: Result<Direction>?) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (directionList) {
            is Result.Success -> items(
                items = directionList.asSuccess().value.data,
                key = { item -> item.id }) { item ->
                DirectionItem()
            }
            is Result.Failure<*> -> {
                directionList.asFailure().error.let { throwable ->
                    items(count = 10) {
                        DirectionItem()
                    }
                }
            }
            else -> {
                items(count = 10) {
                    DirectionItem()
                }
            }
        }
    }
}

@Composable
fun DirectionItem() {
    Column {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .size(width = 200.dp, height = 190.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.test_image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Text(
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    text = "3 455"
                )
            }

        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Text(
            color = Color.Black,
            style = MaterialTheme.typography.subtitle2,
            text = "Туры в Сулакский каньон"
        )
        Spacer(
            modifier = Modifier
                .height(9.dp)
                .fillMaxWidth()
        )
        Text(
            color = colorResource(id = R.color.text_unchecked),
            style = MaterialTheme.typography.subtitle2,
            text = "Сулакский р-н"
        )
    }
}

@Composable
fun TourItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.test_image), contentDescription = null
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                text = "Тур на велосипедах"
            )
        }

    }
}

