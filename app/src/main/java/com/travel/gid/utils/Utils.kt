package com.travel.gid.utils

import android.content.Context
import android.util.TypedValue
import java.text.NumberFormat
import java.util.*

fun valueToDp(value: Float, context: Context): Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics).toInt()
}

fun valueToPrice(value: Int): String{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("RUB")

    return format.format(value)
}