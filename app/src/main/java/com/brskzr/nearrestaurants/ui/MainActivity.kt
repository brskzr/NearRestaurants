package com.brskzr.nearrestaurants.ui

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


val TAG = "MyApp"

class MainActivity : BaseActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_CHECK_SETTINGS = 0x1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getLastLocation() {

        fusedLocationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {
                    val location = taskLocation.result
                    onAccessLocation(location?.latitude ?: 0.toDouble(),
                                             location?.longitude ?: 0.toDouble())

                } else {

                }
            }
    }


    fun onAccessLocation(latitude: Double, longitude: Double) {


    }


    fun createLocationRequest() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...

            val ok = true

            getLastLocation()

        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().


                    val no = false

                    exception.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }



    override fun onStart() {
        super.onStart()

        createLocationRequest()

        return
        getLastLocation()

        return

        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    getPlaces()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */

                    Log.d("TAGGG", "denied")


                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */

                    Log.d("TAGGG", "show permisson")
                    token?.continuePermissionRequest();

                }
            }).check()
    }



    private fun getPlaces() {
        // Use fields to define the data types to return.
        val placeFields: List<Place.Field> = listOf(Place.Field.NAME)

        // Use the builder to create a FindCurrentPlaceRequest.
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
        val placesClient = Places.createClient(this)

        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result
                for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                    Log.i(
                        "TAG",
                        "Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}"
                    )
                }
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Log.e("TAG", "Place not found: ${exception.statusCode}")
                }
            }
        }
    }

}
