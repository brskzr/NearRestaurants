package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.data.models.RestaurantsData
import com.brskzr.nearrestaurants.data.models.Result
import com.brskzr.nearrestaurants.infrastructure.base.ViewResultType
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.utils.DialogUtils
import com.brskzr.nearrestaurants.infrastructure.utils.showSnackbar
import com.brskzr.nearrestaurants.ui.adapters.RestaurantsAdapder
import com.brskzr.nearrestaurants.vm.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_restaurants.*


class RestaurantsFragment : Fragment() {

    private val RADIUS:Long= 15000
    private lateinit var viewModel: MainViewModel
    private lateinit var restaurantsAdapder: RestaurantsAdapder

    var isDataLoaded:Boolean = false
    var currentView:View? = null

    val parent : MainActivity
        get() = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(currentView == null){
            currentView =inflater.inflate(R.layout.fragment_restaurants, container, false)
        }
        else
            isDataLoaded = true


        return currentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = parent.viewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRefresh.setOnClickListener {
            getRestaurants()
        }

        if(isDataLoaded == false) {
            viewModel.restaurantsResult.observe(this, Observer {
                if(it.viewResultType ==ViewResultType.Loading)
                    parent.showLoading()
                else
                    parent.hideLoading()

                if(it.viewResultType == ViewResultType.Error) {
                    requireView().showSnackbar(getString(R.string.FetchingError))
                }
                else if(it.viewResultType == ViewResultType.Success) {
                    loadRestaurants(it.data)
                }
            })

            getRestaurants()
        }
    }

    private fun loadRestaurants(data: MutableList<Result>?) {
        if(data?.any() ?: false){
            restaurantsAdapder = RestaurantsAdapder(data!!, ::onItemClick)
            rvRestaurants.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            rvRestaurants.adapter = restaurantsAdapder
            rvRestaurants.setHasFixedSize(true)
        }
        else{
            requireView().showSnackbar(getString(R.string.NoRestaurantsFound))
        }
    }

    private fun getRestaurants() {
        parent.showLoading()
        viewModel.getRestaurants(app.location.latitude, app.location.longitude, RADIUS)
    }

    private fun onItemClick(item: Result) {
        viewModel.resultOfDetail = item
        parent.navigateToDetail()
    }
}
