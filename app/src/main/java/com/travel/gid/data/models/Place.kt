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
    @SerializedName("geo")
    val geo: Geo,
    @SerializedName("image")
    val image: String,
    @SerializedName("detailImages")
    val detailImages: List<String>,
    @SerializedName("address")
    val address: String,
//    @SerializedName("placeCategories")
//    val placeCategories: List<Unit>
) : Parcelable

@Parcelize
@Serializable
data class Geo(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
) : Parcelable
