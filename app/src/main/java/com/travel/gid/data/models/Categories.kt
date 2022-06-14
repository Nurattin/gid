package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    @SerializedName("data")
    val data: MutableList<CategoriesData>
)
@Serializable
data class CategoriesData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    var enable: Boolean = false
)
