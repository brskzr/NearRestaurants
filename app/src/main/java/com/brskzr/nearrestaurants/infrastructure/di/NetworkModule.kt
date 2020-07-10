package com.brskzr.nearrestaurants.infrastructure.di

import com.brskzr.nearrestaurants.infrastructure.network.DefaultHttpClientFactory
import com.brskzr.nearrestaurants.infrastructure.network.DefaultHttpStringsProvider
import com.brskzr.nearrestaurants.infrastructure.network.HttpClientFactory
import com.brskzr.nearrestaurants.infrastructure.network.HttpStringsProvider
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideHttpStringProvider(): HttpStringsProvider = DefaultHttpStringsProvider()

    @Provides
    fun provideHttpClientFactory(httpStringsProvider: HttpStringsProvider) : HttpClientFactory = DefaultHttpClientFactory(httpStringsProvider)
}