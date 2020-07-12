package com.brskzr.nearrestaurants.infrastructure.location

import android.app.Activity

abstract class LocationHandler {

    protected var next: LocationHandler? = null
    private var activityContext : Activity? = null


    protected val activity : Activity
        get() = activityContext!!

    constructor(next: LocationHandler?, activity: Activity?){
        this.next = next
        this.activityContext = activity
    }

    abstract fun request(finderContext: FinderContext)

    fun release() {
        next?.release()
        next = null
        activityContext = null
    }
}
