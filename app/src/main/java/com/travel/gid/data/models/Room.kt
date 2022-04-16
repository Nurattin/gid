package com.travel.gid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class Room(
    val name: String,
    val price: Int,
    val ameneties: ArrayList<Ameneti>,
    val images: ArrayList<Int>
) : Parcelable