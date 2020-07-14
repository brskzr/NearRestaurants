package com.brskzr.nearrestaurants.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brskzr.nearrestaurants.data.models.RestaurantsData
import com.brskzr.nearrestaurants.data.models.Result
import com.brskzr.nearrestaurants.domain.RestaurantsService
import com.brskzr.nearrestaurants.infrastructure.base.BaseViewModel
import com.brskzr.nearrestaurants.infrastructure.base.ViewResult
import com.brskzr.nearrestaurants.infrastructure.base.ViewResultType
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val restaurantsService: RestaurantsService) : BaseViewModel() {

    private var mRestaurantsResult: MutableLiveData<ViewResult<MutableList<Result>?>> =
        MutableLiveData<ViewResult<MutableList<Result>?>>(ViewResult(null, ViewResultType.Loading))

    val restaurantsResult: LiveData<ViewResult<MutableList<Result>?>>
        get() = mRestaurantsResult

    var resultOfDetail : Result? = null

    fun getRestaurants(lat:Double, long:Double, radius: Long){
        viewModelScope.launch {
            val restaurantsData= restaurantsService.getRestaurants(lat, long, radius).await()

            if(restaurantsData.hasError || restaurantsData.hasValue.not() || (restaurantsData.data?.results?.any() ?: false).not()){
                mRestaurantsResult.postValue(ViewResult(null, ViewResultType.Error))
            }
            else{
                mRestaurantsResult.postValue(ViewResult(restaurantsData.data?.results, ViewResultType.Success))
            }
        }
    }

    fun deleteTemporaryRestaurant(deleteItem: Result) {
        val listOfResult = mRestaurantsResult.value?.data
        listOfResult?.remove(deleteItem)
        mRestaurantsResult.postValue(ViewResult(listOfResult, ViewResultType.Success))
    }
}