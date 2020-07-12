package com.brskzr.nearrestaurants.domain

import com.brskzr.nearrestaurants.data.models.RestaurantsData
import com.brskzr.nearrestaurants.data.repo.RestaurantsRepository
import com.brskzr.nearrestaurants.infrastructure.base.BaseService
import com.brskzr.nearrestaurants.infrastructure.base.DataResult
import com.brskzr.nearrestaurants.infrastructure.network.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class RestaurantsService @Inject constructor (val httpClientFactory: HttpClientFactory) : BaseService(){

    fun getRestaurants(lat:Double, long:Double, radius: Long): Deferred<DataResult<RestaurantsData?>>  {
        return handler.run {
            var api = httpClientFactory.create(RestaurantsRepository::class.java)
            val locationParam = "${lat.toString()},${long.toString()}"

            api.getRestaurantPlaces(locationParam, radius).result()
        }
    }
}