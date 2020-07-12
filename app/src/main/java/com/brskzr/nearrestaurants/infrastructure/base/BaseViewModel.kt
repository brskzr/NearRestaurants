package com.brskzr.nearrestaurants.infrastructure.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

open class BaseViewModel : ViewModel() {

    fun runSafe(running: () -> Unit){
        viewModelScope.launch {
            try {
                running.invoke()
            }
            catch (ex: Exception){

            }
        }

    }

}