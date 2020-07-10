package com.brskzr.nearrestaurants.infrastructure.network

import retrofit2.Call
import java.lang.Exception


fun<T> Call<T>.result(): T? {
    try {
        val response = this.execute()
        if(response.isSuccessful) {
            return response.body()
        }
        return  null
    }
    catch (ex: Exception){
        return null
    }
}