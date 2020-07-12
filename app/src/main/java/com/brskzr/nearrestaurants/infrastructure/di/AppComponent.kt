package com.brskzr.nearrestaurants.infrastructure.di

import android.content.Context
import com.brskzr.nearrestaurants.ui.MainActivity
import com.brskzr.nearrestaurants.ui.RestaurantsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RestauransServiceModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(restaurantsFragment: RestaurantsFragment)
}