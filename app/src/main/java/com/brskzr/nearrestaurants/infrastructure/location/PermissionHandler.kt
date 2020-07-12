package com.brskzr.nearrestaurants.infrastructure.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.utils.DialogUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class PermissionHandler(locationHandler: LocationHandler?, activity: Activity) : LocationHandler(locationHandler, activity) {

    private lateinit var localFinderContext: FinderContext

    private var permissionListener  = object : PermissionListener {

        override fun onPermissionGranted(response: PermissionGrantedResponse) {
            localFinderContext.permission.state = PermissionState.Granted
            next?.request(localFinderContext)
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse) {
            localFinderContext.permission.state = if(response.isPermanentlyDenied) {
                PermissionState.PerminantlyDenied
            }else{
                PermissionState.Denied
            }

            request(localFinderContext)
        }

        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
            token?.continuePermissionRequest()
        }
    }

    override fun request(finderContext: FinderContext) {
        localFinderContext = finderContext

        when(finderContext.permission.state) {
            PermissionState.Denied -> {
                activity.finish()
            }
            PermissionState.PerminantlyDenied -> {
                DialogUtils.alert(activity,
                    activity.resources.getString(R.string.HeaderLocationPermissionRequired),
                    activity.resources.getString(R.string.MessageLocationPermissionSettingsRequired),
                    onOkey =  { navigateToPermissionSettings() },
                    onCancel = { activity.finish() })

            }
            PermissionState.Granted -> {
                finderContext.permission.state = PermissionState.Granted
                next?.request(finderContext)
            }
            PermissionState.Require -> {
                Dexter.withContext(activity)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(permissionListener)
                    .check()
            }
        }
    }

    private fun navigateToPermissionSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", activity.getPackageName(), null)
        intent.data = uri
        activity.startActivity(intent)
    }
}
