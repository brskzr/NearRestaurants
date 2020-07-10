package com.brskzr.nearrestaurants.infrastructure.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.brskzr.nearrestaurants.App

//To get App
val AppCompatActivity.app : App
    get() = this.application as App

//To get AppComponent
val App.di: AppComponent
    get() = this.component

val Fragment.app : App
    get() =this.requireActivity().application as App