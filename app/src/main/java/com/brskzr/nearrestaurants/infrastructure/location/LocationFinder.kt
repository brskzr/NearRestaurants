package com.brskzr.nearrestaurants.infrastructure.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.provider.Settings
import com.brskzr.nearrestaurants.ui.MainActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


typealias LocationObserver = (LocationResult) -> Unit
const val LOCATION_INVERTAL:Long = 10000
const val FASTEST_LOCATION_INTERVAL:Long = 5000
const val REQUEST_CHECK_SETTINGS = 0x1

class LocationFinder {

    private var starterHandler: LocationHandler? = null

    private var finderContext : FinderContext = FinderContext().apply {
        permission.state = PermissionState.Require
        location.isEnabled = false
    }

    @Synchronized
    fun init(activity: Activity) : LocationFinder {

        val gpsHandler = GpsHandler(null, activity)
        val settingsHandler = LocationSettingsHandler(gpsHandler, activity)
        val permissionHandler = PermissionHandler(settingsHandler, activity)

        val finderContext = FinderContext().apply {
            permission.state = PermissionState.Require
            location.isEnabled = false
        }

        starterHandler = permissionHandler

        return this
    }

    @Synchronized
    fun release() {
        starterHandler?.release()
        starterHandler = null
    }

    fun findLocation(){
        starterHandler?.request(finderContext)
    }

    fun observeSuccess(observer: (latitude:Double, longitude:Double) -> Unit ){
        finderContext.successCallback = observer
    }

    fun observeError(observer: (ErrorTypes) -> Unit){
        finderContext.errorCallback = observer
    }
}











