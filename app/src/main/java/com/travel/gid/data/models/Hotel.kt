package com.travel.gid.data.models

import java.io.Serializable

data class Hotel(
    val name: String,
    val price: Int,
    val images: ArrayList<Int>,
    val aboutHotel: ArrayList<Ameneti>,
    val amenetiesHotel: ArrayList<Ameneti>,
    val rooms: ArrayList<Room>
) : Serializable