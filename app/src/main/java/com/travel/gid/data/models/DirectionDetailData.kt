package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class DirectionDetail(
    @SerializedName("data") var data : DirectionDetailData
)

@Serializable
data class DirectionDetailData (

    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("description") var description : String,
    @SerializedName("avatar") var avatar : String,
    @SerializedName("city") var city : City,
    @SerializedName("detail_photo") var images : List<String>,
    @SerializedName("tours") var tours : List<TourDirection>
)

@Serializable
data class TourDirection (
    @SerializedName("id") var id : Long,
    @SerializedName("name") var name : String,
    @SerializedName("operator") var operator : Operator,
    @SerializedName("price") var price : String,
    @SerializedName("people_count") var peopleCount : Int,
    @SerializedName("duration") var duration : String
)

@Serializable
data class Operator (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("image") var image : String
)