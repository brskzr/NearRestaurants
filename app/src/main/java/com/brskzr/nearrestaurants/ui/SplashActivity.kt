package com.brskzr.nearrestaurants.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.utils.DialogUtils
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SplashActivity : BaseActivity() {

    private val LOCATION_INVERTAL:Long = 10000
    private val FASTEST_LOCATION_INTERVAL:Long = 5000
    private val REQUEST_CHECK_SETTINGS = 0x1
    private var isAskedSettings = false
    private var permissionAskedCount = 0
    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        onInit()
    }

    override fun onStart() {
        super.onStart()
        isAskedSettings = false
        permissionAskedCount = 0
        checkPermission()
    }

    private fun onInit() {
        locationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    getLastLocation()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    showPermissionDialog(response.isPermanentlyDenied)
                }
                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    private fun getLastLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER!!, 0.toLong(), 0.toFloat(), object : LocationListener {
            override fun onLocationChanged(p0: Location?) {

            }
        })
    }

    private fun onAccessLocation(latitude: Double, longitude: Double) {
        app.location.latitude = latitude
        app.location.longitude = longitude
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_INVERTAL
            fastestInterval = FASTEST_LOCATION_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            getLastLocation()
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    if(isAskedSettings.not()) {
                        isAskedSettings = true
                        exception.startResolutionForResult(this@SplashActivity, REQUEST_CHECK_SETTINGS)
                    }else{
                        finish()
                    }
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private fun showPermissionDialog(isPermanentlyDenied:Boolean) {
        if(permissionAskedCount <= 1) {
            if(isPermanentlyDenied) {
                DialogUtils.alert(this@SplashActivity,
                    resources.getString(R.string.HeaderLocationPermissionRequired),
                    resources.getString(R.string.MessageLocationPermissionRequired),
                    onOkey =  { navigateToPermissionSettings() },
                    onCancel = { finish() })
            }
            else{
                DialogUtils.alert(this@SplashActivity,
                    resources.getString(R.string.HeaderLocationPermissionRequired),
                    resources.getString(R.string.MessageLocationPermissionRequired),
                    onOkey =  { checkPermission() },
                    onCancel = { finish() })
            }
        }
        else{
            finish()
        }
        permissionAskedCount.inc()
    }

    private fun navigateToPermissionSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", this.getPackageName(), null)
        intent.data = uri
        startActivity(intent)
    }

}
