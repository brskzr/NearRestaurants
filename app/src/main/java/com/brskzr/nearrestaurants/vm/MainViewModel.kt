package com.brskzr.nearrestaurants.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.nearrestaurants.data.models.RestaurantsData
import com.brskzr.nearrestaurants.domain.RestaurantsService
import com.brskzr.nearrestaurants.infrastructure.base.BaseViewModel
import com.brskzr.nearrestaurants.infrastructure.base.ViewResult
import com.brskzr.nearrestaurants.infrastructure.base.ViewResultType
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val restaurantsService: RestaurantsService) : BaseViewModel() {

    private var mRestaurantsResult: MutableLiveData<ViewResult<RestaurantsData?>> =
        MutableLiveData<ViewResult<RestaurantsData?>>(ViewResult(null, ViewResultType.Loading))

    val restaurantsResult: LiveData<ViewResult<RestaurantsData?>>
        get() = mRestaurantsResult


    fun getRestaurants(lat:Double, long:Double, radius: Long){
        viewModelScope.launch {
            val restaurantsData= restaurantsService.getRestaurants(lat, long, radius).await()

            if(restaurantsData.hasError){
                mRestaurantsResult.postValue(ViewResult(null, ViewResultType.Error))
            }
            else{
                mRestaurantsResult.postValue(ViewResult(restaurantsData.data, ViewResultType.Success))
            }
        }
    }
}