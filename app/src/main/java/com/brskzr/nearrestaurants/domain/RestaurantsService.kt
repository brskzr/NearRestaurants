package com.brskzr.nearrestaurants.domain

import com.brskzr.nearrestaurants.data.models.Restaurants
import com.brskzr.nearrestaurants.data.repo.RestaurantsRepository
import com.brskzr.nearrestaurants.infrastructure.base.BaseService
import com.brskzr.nearrestaurants.infrastructure.base.DataResult
import com.brskzr.nearrestaurants.infrastructure.network.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class RestaurantsService @Inject constructor (val httpClientFactory: HttpClientFactory) : BaseService(){

    fun getRestaurants(): Deferred<DataResult<Restaurants?>>  {

        return handler.run {

            val stringProvider = DefaultHttpStringsProvider()
            val httpClientFactory = DefaultHttpClientFactory(stringProvider)

            val api = httpClientFactory.create(RestaurantsRepository::class.java)
            val result = api.getPlaces().execute().body()

            result
        }
    }
}