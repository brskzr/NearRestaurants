package com.brskzr.nearrestaurants.infrastructure.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHttpStringsProvider @Inject constructor(): HttpStringsProvider {
    override val BASE_URL: String
        get() = "TODO URL"
}