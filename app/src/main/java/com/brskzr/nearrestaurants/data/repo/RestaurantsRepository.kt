package com.brskzr.nearrestaurants.data.repo

import com.brskzr.nearrestaurants.BuildConfig
import com.brskzr.nearrestaurants.data.models.Restaurants
import com.brskzr.nearrestaurants.data.models.RestaurantsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestaurantsRepository {

    @GET("maps/api/place/nearbysearch/json?keyword=Restaurants&key=${BuildConfig.PLACES_API_KEY}")
    fun getRestaurantPlaces(@Query("location") location:String,
                            @Query("radius") radius: Long) : Call<RestaurantsData>
}


