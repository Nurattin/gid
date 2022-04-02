package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Direction (
    @SerializedName("data")
    val data: List<DirectionData>
)

@Serializable
data class DirectionData (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("city")
    val city: City
)

