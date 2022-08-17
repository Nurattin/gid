package com.travel.gid.ui.tour_list.compose
//
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.Text
//import androidx.compose.material.ripple.rememberRipple
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import com.travel.gid.R
//import com.travel.gid.data.models.TourData
//import com.travel.gid.ui.tour_list.viewModel.TourViewModel
//import com.travel.gid.utils.Response
//
//@Composable
//fun TourLazyList(
//    viewModel: TourViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
//    onClickGoToDetailedTour: (Long) -> Unit,
//    onClickShowBottomSheetFragment: () -> Unit,
//    onClickGetSortedList: (Int) -> Unit,
//    onClickChangeCategories: (Int) -> Unit,
//    onClickRefreshDate: () -> Unit,
//    response: Response
//) {
//
//    Log.i("recomposition", "count()")
//
//    val listTours by viewModel.tours.observeAsState()
//
//    LazyColumn(
//        contentPadding = PaddingValues(11.dp),
//        verticalArrangement = Arrangement.spacedBy(15.dp),
//        content = {
//            when (response) {
//                Response.Loading -> {
//                    item {
//                        ComposeProgressBar()
//                    }
//                }
//                Response.Success -> {
//                    listTours?.body()?.data?.let { listTour ->
//                        items(items = listTour.chunked(4)) {
//                            when (it.size) {
//                                1 -> {
//                                    ItemTour(
//                                        coefficient = 1.64,
//                                        item = it[0],
//                                        onClickGoToDetailedTour = onClickGoToDetailedTour
//                                    )
//                                }
//                                2 -> {
//                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                                        ItemTour(
//                                            coefficient = 1.64,
//                                            item = it[0],
//                                            onClickGoToDetailedTour = onClickGoToDetailedTour
//                                        )
//                                        ItemTour(
//                                            coefficient = 1.29,
//                                            item = it[1],
//                                            onClickGoToDetailedTour = onClickGoToDetailedTour
//                                        )
//                                    }
//                                }
//                                3 -> {
//                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                                        Column(
//                                            modifier = Modifier,
//                                            verticalArrangement = Arrangement.spacedBy(15.dp)
//                                        ) {
//                                            ItemTour(
//                                                coefficient = 1.64,
//                                                item = it[0],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                            ItemTour(
//                                                coefficient = 1.29,
//                                                item = it[2],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                        }
//                                        ItemTour(
//                                            coefficient = 1.29,
//                                            item = it[1],
//                                            onClickGoToDetailedTour = onClickGoToDetailedTour
//                                        )
//                                    }
//                                }
//                                4 -> {
//                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                                        Column(
//                                            modifier = Modifier,
//                                            verticalArrangement = Arrangement.spacedBy(15.dp)
//                                        ) {
//                                            ItemTour(
//                                                coefficient = 1.64,
//                                                item = it[0],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                            ItemTour(
//                                                coefficient = 1.29,
//                                                item = it[2],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                        }
//                                        Column(
//                                            modifier = Modifier,
//                                            verticalArrangement = Arrangement.spacedBy(15.dp)
//                                        ) {
//                                            ItemTour(
//                                                coefficient = 1.29,
//                                                item = it[1],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                            ItemTour(
//                                                coefficient = 1.64,
//                                                item = it[3],
//                                                onClickGoToDetailedTour = onClickGoToDetailedTour
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                else -> {
//                    item {
////                        ErrorConnection(onClickRefreshDate = onClickRefreshDate)
//                    }
//                }
//            }
//        })
//}
//
//
//@Composable
//fun ItemTour(item: TourData, coefficient: Double, onClickGoToDetailedTour: (Long) -> Unit) {
//    val screenWidth = LocalConfiguration.current.screenWidthDp
//    Card(
//        modifier = Modifier
//            .height(((screenWidth * 0.470) * coefficient).dp)
//            .width((screenWidth * 0.470).dp)
//            .clip(shape = RoundedCornerShape(8.dp))
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = rememberRipple(),
//                onClick = { onClickGoToDetailedTour(item.id) }),
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            AsyncImage(
//                modifier = Modifier.fillMaxSize(),
//                model = item.avatar,
//                contentDescription = null,
//                contentScale = ContentScale.Crop
//            )
//            Column(
//                modifier = Modifier
//                    .background(color = Color.White.copy(alpha = 0f))
//                    .padding(13.dp)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_baseline_favorite_border),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .align(Alignment.End)
//                        .clickable(interactionSource = remember { MutableInteractionSource() },
//                            indication = rememberRipple(bounded = false, radius = 20.dp),
//                            onClick = {})
//
//                )
//                Column {
//                    Text(
//
//                        text = item.name,
//                        modifier = Modifier, color = Color.White,
//                        fontSize = 18.sp, fontWeight = FontWeight(500)
//                    )
//                    Row {
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_baseline_place),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .wrapContentWidth()
//                        )
//                        Text(
//                            text = "с. Чох, окр. Загутли",
//                            modifier = Modifier, color = Color.White,
//                            fontSize = 11.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
