package com.travel.gid.data.models

data class FilterParamsTour(
    var categoriesName: List<Categories>? = null,
    var categoriesAccept: List<Int>? = null,
    var startPrice: Int? = null,
    var endPrice: Int? = null,
    var sortedParams: String? = null,
) {

    fun fill(categoriesNames: List<Categories>?, startPrice: Int?, endPrice: Int?) {
        this.categoriesName = categoriesNames
        this.startPrice = startPrice
        this.endPrice = endPrice
    }
}