package com.travel.gid.data.models

data class CategoriesHome(
    val id: Int,
    val iconChecked: Int,
    val iconUnChecked: Int,
    val name: String,
    val isChecked: Boolean = false
)
