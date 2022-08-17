package com.travel.gid.data.models

data class FilterParams(
    var categories: List<Int>? = null,
    var startPrice: Int? = null,
    var endPrice: Int? = null,
    var orderByPrice: String? = null,
)