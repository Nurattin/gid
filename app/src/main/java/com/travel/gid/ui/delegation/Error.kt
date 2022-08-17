package com.travel.gid.ui.delegation

import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.travel.gid.utils.compose.ErrorConnection
import com.travel.gid.utils.makeGone
import com.travel.gid.utils.makeVisibly

interface  Error {
    fun showError(
        mainContainer: View,
        errorContainer: View,
        throwable: Throwable? = null,
        actionOnErrorButton: (() -> Unit)? = null,
    )
    fun hideError(mainContainer: View, errorContainer: View)
}

class ErrorImpl : Error {

    override fun showError(
        mainContainer: View,
        errorContainer: View,
        throwable: Throwable?,
        actionOnErrorButton: (() -> Unit)?
    ) {
        mainContainer.makeGone()
        errorContainer.makeVisibly()
        with(errorContainer as ComposeView) {
            this.setContent {
                ErrorConnection(textError = "Unknown error") {
                    actionOnErrorButton?.invoke()
                }
            }
        }
    }

    override fun hideError(mainContainer: View, errorContainer: View) {
        mainContainer.makeVisibly()
        errorContainer.makeGone()
    }

}