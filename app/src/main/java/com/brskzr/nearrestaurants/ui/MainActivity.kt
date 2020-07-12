package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.di.di
import com.brskzr.nearrestaurants.vm.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        this.app.di.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(R.id.container, RestaurantsFragment())
    }

    private fun init() {

        viewModel.restaurantsResult.observe(this, Observer {

            val x = it
        })

        viewModel.getRestaurants(app.location.latitude, app.location.longitude, 15000)
    }

}
