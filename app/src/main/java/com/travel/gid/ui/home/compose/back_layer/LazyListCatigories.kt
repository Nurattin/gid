package com.travel.gid.ui.home.compose.back_layer

import androidx.annotation.ColorRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.travel.gid.R


enum class HomeScreen{
    TourScreen, GidScreen, PlaceScreen, HotelScreen
}

data class Categories(
    val icons: ImageVector,
    val name: String,
    val isChecked: MutableState<Boolean>,
    @ColorRes val textUncheckedColor: Int = R.color.text_unchecked,
    @ColorRes val textCheckedColor: Int = R.color.text_checked,
    @ColorRes val iconBackgroundCheckedColor: Int = R.color.icon_background_checked,
    @ColorRes val iconBackgroundUncheckedColor: Int = R.color.icon_background_unchecked,
    @ColorRes val iconCheckedColor: Int = R.color.icon_checked,
    @ColorRes val iconUncheckedColor: Int = R.color.icon_unchecked,
)

@Composable
fun LazyListCategories(
    listCategories: List<Categories>,
    modifier: Modifier = Modifier,
    onClickChangeCategories: (newPosition: Int) -> Unit,

    ) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(20.dp)
    ) {
        itemsIndexed(
            items = listCategories,
            key = { _, item ->
                item.name
            }
        ) { index, item ->
            CategoriesItem(
                onClickChangeCategories = { onClickChangeCategories(index) },
                item = item,
            )
        }
    }
}

@Composable
fun CategoriesItem(
    onClickChangeCategories: () -> Unit,
    item: Categories, modifier: Modifier = Modifier,
) {
    Column {
        val transition = updateTransition(targetState = item.isChecked.value, label = "categories_item")

        val colorBackgroundCardAnimation by transition.animateColor(label = "categories_item") { state ->
            when (state) {
                true -> colorResource(id = item.iconBackgroundCheckedColor)
                false -> colorResource(id = item.iconBackgroundUncheckedColor)
            }
        }

        val sizeIconAnimation by transition.animateDp(label = "categories_item") { state ->
            when (state) {
                true -> 30.dp
                false -> 25.dp
            }
        }
        Card(
            elevation = 3.dp,
            modifier = modifier
                .height(50.dp)
                .width(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        color = Color.Black
                    ),
                    onClick = onClickChangeCategories
                ),
            backgroundColor = colorBackgroundCardAnimation
        ) {

            Icon(
                modifier = Modifier
                    .wrapContentSize(align = Center)
                    .size(sizeIconAnimation),
                imageVector = item.icons,
                contentDescription = null,
                tint = if (item.isChecked.value) colorResource(id = item.iconCheckedColor)
                else colorResource(id = item.iconUncheckedColor)
            )
        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            style = MaterialTheme.typography.body2,
            color = if (item.isChecked.value) colorResource(id = item.textCheckedColor)
            else colorResource(id = item.textUncheckedColor),
            text = item.name
        )
    }
}