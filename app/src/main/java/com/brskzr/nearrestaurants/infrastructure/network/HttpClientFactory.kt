package com.brskzr.nearrestaurants.infrastructure.network

interface HttpClientFactory {
    fun<T> create(serviceClass: Class<T>) : T
}