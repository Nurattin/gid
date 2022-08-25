package com.travel.gid.ui.home.compose.back_layer

import android.util.Log
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage



@Composable
fun LazyListBanner(
    modifier: Modifier = Modifier,
    listItem: List<String>
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = listItem) { item ->
            BannerItem(item = item)
        }
    }



}

@Composable
fun BannerItem(item: String) {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(width = 320.dp, height = 220.dp)
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}