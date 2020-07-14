package com.brskzr.nearrestaurants.infrastructure.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.brskzr.nearrestaurants.BuildConfig
import com.brskzr.nearrestaurants.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


fun ImageView.glide(url:String) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(this.context).load(url).apply(requestOptions).into(this)
}

fun ImageView.gooplePhoto(reference: String) {
    try {
        val url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${reference}&key=${BuildConfig.PLACES_API_KEY}"
        this.glide(url)
    }
    catch (ex: Exception){
        Glide.with(this.context).load(R.drawable.ic_cutlery).into(this)
    }
}


fun View.showSnackbar(message: String){
    Snackbar.make(this, message , Snackbar.LENGTH_LONG).show()
}