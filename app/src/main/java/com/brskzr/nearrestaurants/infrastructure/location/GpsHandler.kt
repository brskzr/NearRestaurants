package com.brskzr.nearrestaurants.infrastructure.location

import android.app.Activity
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GpsHandler(locationHandler: LocationHandler?, activity: Activity) : LocationHandler(locationHandler, activity) {

    private val MAX_TRY_COUNT = 10
    private var tryCount = 0
    private lateinit var localFinderContext: FinderContext
    private var client: FusedLocationProviderClient? = null

    private var locationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            tryCount = tryCount.inc()

            if(tryCount >= MAX_TRY_COUNT) {
                client?.removeLocationUpdates(this)
                CoroutineScope(Dispatchers.Main).launch {
                    localFinderContext.errorCallback?.invoke(ErrorTypes.LocationNotFound)
                }
            }

            locationResult?.let {
                for (location in locationResult.locations){
                    client?.removeLocationUpdates(this)
                    CoroutineScope(Dispatchers.Main).launch {
                        localFinderContext.successCallback?.invoke(location.latitude, location.longitude)
                    }
                    break
                }
            }
        }
    }

    override fun request(finderContext: FinderContext) {
        localFinderContext = finderContext

        if(client == null) {
            client  = LocationServices.getFusedLocationProviderClient(activity)

            client?.let {
                val locationRequest = LocationRequest.create()?.apply {
                    interval = 1000
                    fastestInterval = 500
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                it.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper())
            }
        }
    }

}