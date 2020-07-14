package com.brskzr.nearrestaurants.data.models

data class RestaurantsData(
    val html_attributions: List<Any>,
    var results: MutableList<Result>,
    val status: String
)