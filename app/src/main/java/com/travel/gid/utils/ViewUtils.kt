package com.travel.gid.utils

import android.view.View
import androidx.core.view.isVisible

fun View.makeVisibleIfElseGone(bool: Boolean) {
    isVisible = bool
}

fun View.makeGoneIfElseVisible(bool: Boolean) {
    isVisible = !bool
}

fun View.makeVisibleIf(bool: Boolean) {
    if (bool) isVisible = bool
}