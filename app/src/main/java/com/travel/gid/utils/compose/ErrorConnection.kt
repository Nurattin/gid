package com.travel.gid.utils.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.travel.gid.R


@Composable
fun ErrorConnection(textError: String, onClickRefreshDate: (() -> Unit)? = null) {
    val screenWidth = LocalConfiguration.current.screenHeightDp
    Column(

        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_internet_connection))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            modifier = Modifier.height((screenWidth / 2).dp),
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Inside
        )

        OutlinedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 80.dp),
        onClick = { onClickRefreshDate?.invoke() }) {
            Text(text = "Обновить")
        }
    }
}