package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
