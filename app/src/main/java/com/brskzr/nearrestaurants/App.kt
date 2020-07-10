package com.brskzr.nearrestaurants

import android.app.Application
import com.brskzr.nearrestaurants.infrastructure.di.AppComponent
import com.brskzr.nearrestaurants.infrastructure.di.DaggerAppComponent
import com.google.android.libraries.places.api.Places

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this.applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        configure()
    }

    private fun configure() {
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        Places.initialize(applicationContext, BuildConfig.PLACES_API_KEY)
    }

    inner class ExceptionHandler : Thread.UncaughtExceptionHandler{
        override fun uncaughtException(t: Thread, e: Throwable) {
            handleError(t, e)
        }

        private fun handleError(t: Thread, e: Throwable) {
            System.exit(0)
        }
    }
}


