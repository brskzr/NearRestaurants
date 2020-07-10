package com.brskzr.nearrestaurants

import android.app.Application
import com.brskzr.nearrestaurants.infrastructure.di.AppComponent
import com.brskzr.nearrestaurants.infrastructure.di.DaggerAppComponent
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this.applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        configure()
    }

    private fun configure() {
        Places.initialize(applicationContext, BuildConfig.PLACES_API_KEY)
    }
}


