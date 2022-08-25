package com.travel.gid.ui.home.compose.front_layer

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.travel.gid.R
import com.travel.gid.data.models.GidDetail
import com.travel.gid.data.result.Result
import com.travel.gid.data.result.asFailure
import com.travel.gid.data.result.asSuccess
import com.travel.gid.ui.home.view_model.HomeViewModel
import com.travel.gid.utils.compose.ComposeProgressBar
import com.travel.gid.utils.compose.ErrorConnection


@Composable
fun GidScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val result by viewModel.gidList

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        when (result) {
            is Result.Success -> items(
                items = (result.asSuccess().value.data),
                key = { item -> item.id }) { item ->
                GidCard(item = item)
            }
            is Result.Failure<*> -> {
                (result as Result.Failure<*>).asFailure().error.let { throwable ->
                    item {
                        ErrorConnection(textError = "Биляяя", onClickRefreshDate = {
                            viewModel.getGidList()
                        })
                    }
                }
            }
            is Result.Loading -> item {
                GidProgressBarIndicator(Modifier)
            }
        }
    }
}


@Composable
fun GidProgressBarIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {


        CircularProgressIndicator(
            modifier = modifier,
            strokeWidth = 3.dp,
            color = Color.Gray
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GidCard(item: GidDetail) {

    var expandable by rememberSaveable {
        mutableStateOf(false)
    }
    val gidCharacteristic = rememberSaveable {
        hashMapOf(
            "Туров" to "${(0..20).random()}",
            "Рейтинг" to "${(0..10).random()}",
            "Средний чек" to "${item.averageCheck} ₽",
        )
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 3.dp,
        onClick = {
            expandable = !expandable
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
            )
            {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GidAvatar(avatar = item.image)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(21.dp)
                    ) {
                        GidName(name = item.name)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            gidCharacteristic.forEach {
                                GidCharacteristic(
                                    characteristicName = it.key,
                                    characteristicItem = it.value
                                )
                            }
                        }
                    }
                }
                if (expandable) {
                    AdditionalInformation(item)
                }
            }
        }
    }
}

@Composable
private fun AdditionalInformation(item: GidDetail) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(11.dp)
    )
    Divider()
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(11.dp)
    )
    Text(
        fontWeight = FontWeight(300),
        style = MaterialTheme.typography.body1,
        text = "Галерея"
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ListGallery(item.detailImages)
        ButtonContactTheGuide()
    }
}


@Composable
fun GidName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h6,
        color = Color.Black,
        fontWeight = FontWeight(300)
    )
}


@Composable
fun GidCharacteristic(
    characteristicName: String,
    characteristicItem: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.subtitle2,
            text = characteristicName,
            fontWeight = FontWeight(300),
            color = Color.Black
        )
        Text(
            style = MaterialTheme.typography.subtitle1,
            text = characteristicItem,
            fontWeight = FontWeight(400),
            color = colorResource(id = R.color.text_unchecked)
        )
    }
}

@Composable
fun GidAvatar(avatar: String) {
    SubcomposeAsyncImage(
        loading = { ComposeProgressBar() },
        modifier = Modifier
            .size(90.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.pine_green),
                shape = RoundedCornerShape(8.dp)
            ),
        model = avatar,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ListGallery(listPhoto: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        listPhoto.forEachIndexed { index, item ->
            if (index == 3) {
                Image(
                    modifier = Modifier
                        .size(height = 40.dp, width = 30.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = colorResource(id = R.color.pine_green)),
                    imageVector = Icons.Outlined.Collections,
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = null
                )
                return@forEachIndexed
            }
            GalleyItem(item = item)
        }

    }

}


@Composable
fun GalleyItem(
    item: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .size(height = 40.dp, width = 30.dp)
            .clip(RoundedCornerShape(4.dp)),
        model = item,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}


@Preview
@Composable
fun ButtonContactTheGuide() {
    val context = LocalContext.current
    OutlinedButton(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.pine_green)),
        onClick = {
            Toast.makeText(context, "Абонент умер сори(", Toast.LENGTH_SHORT).show()
        }) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.Call,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.pine_green))
            )
            Text(
                text = "Связаться",
                style = MaterialTheme.typography.subtitle2,
                color = colorResource(id = R.color.pine_green)
            )
        }
    }
}
