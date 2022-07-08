package com.travel.gid.utils

import android.R
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.ceil


fun getDeviceHeight(context: Context): Int {
    val resources: Resources = context.resources
    val metrics: DisplayMetrics = resources.displayMetrics
    return metrics.heightPixels
}
