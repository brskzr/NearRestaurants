package com.brskzr.nearrestaurants.infrastructure.location

import android.app.Activity
import android.content.IntentSender
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LocationSettingsHandler(locationHandler: LocationHandler?, activity: Activity) : LocationHandler(locationHandler, activity) {

    private var timer: Timer? = null
    private lateinit var localFinderContext: FinderContext

    override fun request(finderContext: FinderContext) {
        localFinderContext = finderContext

        if(LocationUtils.isGpsEnabled(activity) && LocationUtils.isNetworkEnabled(activity)){
            finderContext.location.isEnabled = true
            next?.request(finderContext)
        }
        else{
            checkUpLocationSettings()
        }
    }

    private fun checkUpLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_INVERTAL
            fastestInterval = FASTEST_LOCATION_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    exception.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                }
                catch (sendEx: IntentSender.SendIntentException) {}
            }
        }
        startTimer()
    }

    private fun onSuccess() {
        localFinderContext.location.isEnabled = true
        next?.request(localFinderContext)
    }

    private fun startTimer(){
        timer = Timer()
        timer?.schedule(LocationEnabledTask(), 100, 500)
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun error() {
        CoroutineScope(Dispatchers.Main).launch {
            localFinderContext.errorCallback?.invoke(ErrorTypes.LocationSettingsNotAllowed)
        }
    }

    inner class LocationEnabledTask : TimerTask() {

        private val MAX_TRY_COUNT = 16
        private var tryCount = 0

        override fun run() {
            tryCount = tryCount.inc()

            if(tryCount >= MAX_TRY_COUNT) {
                if(LocationUtils.isGpsEnabled(activity) && LocationUtils.isNetworkEnabled(activity)){
                    localFinderContext.location.isEnabled = true
                    next?.request(localFinderContext)
                    stopTimer()
                    return
                }

                error()
                stopTimer()
            }

            if(LocationUtils.isGpsEnabled(activity) && LocationUtils.isNetworkEnabled(activity)){
                localFinderContext.location.isEnabled = true
                next?.request(localFinderContext)
                stopTimer()
            }
        }
    }
}
