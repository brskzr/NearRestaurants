package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import android.view.View
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.di.di
import com.brskzr.nearrestaurants.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
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

    fun showLoading() {
        rvLoading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        rvLoading.visibility = View.GONE
    }

    fun navigateToDetail() {
        replaceFragment(R.id.container,
            DetailFragment()
        )
    }
}
