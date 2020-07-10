package com.brskzr.nearrestaurants

import android.app.Application
import com.brskzr.nearrestaurants.infrastructure.di.AppComponent
import com.brskzr.nearrestaurants.infrastructure.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this.applicationContext)
    }
}


