package com.travel.gid.utils

interface ExceptionMapper {
    fun map(exception: Throwable): String
}