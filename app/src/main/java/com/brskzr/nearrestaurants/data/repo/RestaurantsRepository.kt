package com.brskzr.nearrestaurants.data.repo

import com.brskzr.nearrestaurants.data.models.Restaurants
import retrofit2.Call


interface RestaurantsRepository {

    fun getPlaces() : Call<Restaurants>
}