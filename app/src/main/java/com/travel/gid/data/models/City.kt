package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
class City(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)