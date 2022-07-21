package com.travel.gid.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip

fun View.makeVisibly() {
    isVisible = true
}

fun View.makeGone() {
    isVisible = false
}

fun View.makeGoneIfVisibly(){
    if (this.isVisible) this.isVisible = false
}

fun View.makeVisiblyIfGone(){
    if (!this.isVisible) this.isVisible = true
}

fun SwipeRefreshLayout.stopRefreshed(){
    isRefreshing = false
}

fun SwipeRefreshLayout.showRefreshed(){
    isRefreshing = true
}
fun Chip.selected(){
    isChecked = true
}

fun Chip.removeSelected(){
    isChecked = false
}

