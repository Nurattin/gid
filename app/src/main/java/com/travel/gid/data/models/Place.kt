package com.travel.gid.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val data: Places
)

@Parcelize
@Serializable
data class Places(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("stars")
    val stars: Int,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("image")
    val image: String,
    @SerializedName("detailImages")
    val detailImages: List<String>,
    @SerializedName("address")
    val address: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("placeCategories")
    val placeCategories: List<Unit>
): Parcelable