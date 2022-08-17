package com.travel.gid.data.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}