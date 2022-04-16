package com.travel.gid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Ameneti(val name: String, val img: Int): Parcelable