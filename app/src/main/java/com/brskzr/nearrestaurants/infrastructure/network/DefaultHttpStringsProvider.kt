package com.brskzr.nearrestaurants.infrastructure.network

import com.brskzr.nearrestaurants.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHttpStringsProvider @Inject constructor(): HttpStringsProvider {
    override val BASE_URL: String
        get() = BuildConfig.PLACES_API_KEY
}