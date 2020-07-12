package com.brskzr.nearrestaurants.infrastructure.location

import android.content.Context
import android.location.LocationManager
import java.lang.Exception

object LocationUtils {

    fun isGpsEnabled(context: Context) : Boolean =
        try {
            getLocationManager(context).isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
        catch (ex: Exception){
            false
        }

    fun isNetworkEnabled(context: Context) : Boolean =
        try {
            getLocationManager(context).isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
        catch (ex: Exception){
            false
        }

    private fun getLocationManager(context: Context) = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}