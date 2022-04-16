package com.travel.gid.data.models

data class HotelDetail(
    var name: String,
    var price: String,
    var images:  ArrayList<String>,
    val aboutHotel: ArrayList<AboutHotel>,
    val ameneties: ArrayList<Ameneti>
)

data class AboutHotel(val name: String, val img: Int)
