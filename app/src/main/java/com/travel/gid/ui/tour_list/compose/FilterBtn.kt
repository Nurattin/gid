package com.travel.gid.ui.tour_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.travel.gid.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FilterBtn(onClickShowBottomSheetFilter: () -> Unit) {

    var enable by remember {
        mutableStateOf(true)
    }

    val composableScope = rememberCoroutineScope()


    Row(
        modifier = Modifier.clickable(
            enabled = enable,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                composableScope.launch {
                    enable = false
                    onClickShowBottomSheetFilter()
                    delay(1000)
                    enable = true
                }
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {

        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_coolicon),
            contentDescription = "Фильтры"
        )

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.filter),
            fontWeight = FontWeight(400),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}