package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Tour (
    @SerializedName("data")
    val data: List<TourData>
)

@Serializable
data class TourData (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val avatar: String
)

data class Links (
    val first: String,
    val last: String,
    val prev: Any? = null,
    val next: Any? = null
)

data class Meta (

    val currentPage: Long,

    val from: Long,

    val lastPage: Long,

    val links: List<Link>,
    val path: String,

    val perPage: Long,

    val to: Long,
    val total: Long
)

data class Link (
    val url: String? = null,
    val label: String,
    val active: Boolean
)

