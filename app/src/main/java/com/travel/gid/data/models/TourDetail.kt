package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName


data class TourDetail (
    @SerializedName("data") var data : TourDetailData
)

data class TourDetailData (

    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("description") var description : String,
    @SerializedName("image") var avatar : String,
    @SerializedName("startFrom") var startFrom : String,
    @SerializedName("category") var categoryTour : CategoryTour,
    @SerializedName("operator") var operator : Operator,
    @SerializedName("price") var price : String,
    @SerializedName("duration") var duration : String,
    @SerializedName("peopleCount") var peopleCount : Int,
    @SerializedName("lunch") var lunch : String,
    @SerializedName("detailImages") var detailPhoto : List<String>,
    @SerializedName("places") var places: List<Places>
)


data class CategoryTour (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String
)

