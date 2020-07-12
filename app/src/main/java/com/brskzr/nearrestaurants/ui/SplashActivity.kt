package com.brskzr.nearrestaurants.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.location.ErrorTypes
import com.brskzr.nearrestaurants.infrastructure.location.LocationFinder
import com.brskzr.nearrestaurants.infrastructure.utils.DialogUtils
import com.brskzr.nearrestaurants.infrastructure.utils.InternetUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity() {

    private lateinit var locationFinder: LocationFinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    override fun onStart() {
        super.onStart()
        checkInternet()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationFinder.release()
    }

    fun init() {
        locationFinder = LocationFinder().init(this)
        locationFinder.observeError {
            handleErrors(it)
        }
        locationFinder.observeSuccess({ latitude, longitude ->
            onAccessLocation(latitude, longitude)
        })

    }

    fun checkInternet() {
        InternetUtils.checkInternetConnection(this, success =  {
            locationFinder.findLocation()
        }, error = {

            DialogUtils.alert(this,
                resources.getString(R.string.HeaderWarning),
                resources.getString(R.string.MessagePleaseCheckInternetConnection),
                onOkey =  {  },
                onCancel = { finish() })

        })

    }


    private fun handleErrors(locationErrorTypes: ErrorTypes) {

        var message = when(locationErrorTypes) {
            ErrorTypes.LocationSettingsNotAllowed -> {
                resources.getString(R.string.MessageLocationSettingsNotAllowed)
            }
            ErrorTypes.LocationNotFound -> {
                resources.getString(R.string.MessageLocationNotFound)
            }
        }

        DialogUtils.alert(this,
            resources.getString(R.string.HeaderWarning),
            message,
            onOkey =  { locationFinder.findLocation() },
            onCancel = { finish() })
    }

    private fun onAccessLocation(latitude: Double, longitude: Double) {

        Toast.makeText(this, "Location taken", Toast.LENGTH_SHORT).show()

        GlobalScope.launch(Dispatchers.Main) {
            app.location.latitude = latitude
            app.location.longitude = longitude
            navigatToMain()
            this@SplashActivity.finish()
        }

//        val handler = Handler()
//        handler.postDelayed({
//            finish()
//        }, 1000)
    }

}
