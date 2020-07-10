package com.brskzr.nearrestaurants.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }


}
