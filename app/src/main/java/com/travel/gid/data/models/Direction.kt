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
    @SerializedName("image")
    val avatar: String,
    @SerializedName("cities")
    val city: City
)

