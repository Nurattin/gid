package com.travel.gid.utils

import com.travel.gid.ui.home.child_fragments.gid_fragment.exceptons.FuckException

object BaseExceptionMapper: ExceptionMapper {
    override fun map(exception: Throwable) =
        when (exception) {
            is NullPointerException -> "FUCK"
            is FuckException -> ";IllegalArgumentException"
            else -> "Unknown error"
        }
}