package com.brskzr.nearrestaurants.data.models

data class RestaurantsData(
    val html_attributions: List<Any>,
    val results: List<Result>,
    val status: String
)