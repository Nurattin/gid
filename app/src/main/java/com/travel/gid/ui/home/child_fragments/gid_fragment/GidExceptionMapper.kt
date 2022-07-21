package com.travel.gid.ui.home.child_fragments.gid_fragment

import com.travel.gid.ui.home.child_fragments.gid_fragment.exceptons.FuckException
import com.travel.gid.utils.ExceptionMapper

internal object GidExceptionMapper : ExceptionMapper {

    override fun map(exception: Throwable) =
        when (exception) {
            is NullPointerException -> "FUCK"
            is FuckException -> ";igvheihifhefi"
            else -> "Unknown error"
        }
}