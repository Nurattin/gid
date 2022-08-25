package com.travel.gid.ui.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeAppBar() {

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                color = Color.Black,
                style = MaterialTheme.typography.h4,
                text = "GID",
                fontSize = 36.sp,
                fontWeight = FontWeight(250)
            )
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Махачкала",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Rounded.Place,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 2.dp
    )
}