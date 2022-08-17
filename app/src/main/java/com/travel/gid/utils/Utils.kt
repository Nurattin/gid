package com.travel.gid.utils

import android.content.Context
import android.text.format.DateFormat
import android.util.TypedValue
import java.text.NumberFormat
import java.util.*

fun valueToDp(value: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        context.resources.displayMetrics
    ).toInt()
}

fun valueToPrice(value: Int): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("RUB")

    return format.format(value)
}

fun getDateFromTimestamp(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp
    val date = DateFormat.format("dd-MMMM", calendar).toString()
    return date
}

fun ConnectionLiveData.checkInternetConnection(
    actionOnInternetConnection: () -> Unit,
    actionOnInternetDisconnection: (() -> Unit)? = null
) {
    when (this.value) {
        true -> actionOnInternetConnection()
        false -> actionOnInternetDisconnection?.let { it() }
        null -> actionOnInternetDisconnection?.let { it() }
    }
}

fun Response.change(response: Response): Response{
    if (this != response) return response
    return this
}