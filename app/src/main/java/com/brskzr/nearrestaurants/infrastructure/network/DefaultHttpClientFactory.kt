package com.brskzr.nearrestaurants.infrastructure.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultHttpClientFactory @Inject constructor (val urlProvider: HttpStringsProvider) : HttpClientFactory {

    private var instance : Retrofit? = null

    @Synchronized
    private fun getInstance() : Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(urlProvider.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }

    override fun<T> create(serviceClass: Class<T>) : T = getInstance().create(serviceClass)
}


