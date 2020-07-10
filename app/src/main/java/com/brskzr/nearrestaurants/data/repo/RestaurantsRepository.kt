package com.brskzr.nearrestaurants.data.repo

import com.brskzr.nearrestaurants.data.models.Restaurants
import retrofit2.Call


interface RestaurantsRepository {

    fun getPlaces() : Call<Restaurants>
}


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.066666,29.916668&radius=15000&type=restaurant&keyword=Restaurants&key=AIzaSyB2bBfZn3Ih2-d2Q4GQVcwJ4QYrCqwWHE8