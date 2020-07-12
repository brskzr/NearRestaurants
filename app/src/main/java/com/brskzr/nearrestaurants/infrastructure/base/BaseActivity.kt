package com.brskzr.nearrestaurants.infrastructure.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.brskzr.nearrestaurants.ui.ErrorActivity
import com.brskzr.nearrestaurants.ui.MainActivity

open class BaseActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
    }

    fun onError() {
        val errorIntent = Intent(this, ErrorActivity::class.java)
        startActivity(errorIntent)
        finish()
    }

    fun navigatToMain() {
        val errorIntent = Intent(this, MainActivity::class.java)
        startActivity(errorIntent)
        finish()
    }

    fun addFragment(containerId:Int, fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .commit()
    }

    fun replaceFragment(containerId:Int, fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    inner class ExceptionHandler : Thread.UncaughtExceptionHandler{
        override fun uncaughtException(t: Thread, e: Throwable) {
            handleError(t, e)
        }

        private fun handleError(t: Thread, e: Throwable) {
            onError()
        }
    }
}