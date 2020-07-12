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
import com.brskzr.nearrestaurants.ui.adapters.RestaurantsAdapder
import com.brskzr.nearrestaurants.vm.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_restaurants.*


class RestaurantsFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    val parent : MainActivity
        get() = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurants, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = parent.viewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.restaurantsResult.observe(this, Observer {
            if(it.viewResultType == ViewResultType.Loading){
                parent.showLoading()
            }else if(it.viewResultType == ViewResultType.Error) {
                parent.hideLoading()
                setVisibilityRefresh(true)

                val snackbar = Snackbar
                    .make(view, getString(R.string.ErrorRestaurants), Snackbar.LENGTH_LONG)
                    snackbar.show()
            }
            else if(it.viewResultType == ViewResultType.Success) {
                loadRestaurants(it.data)
                parent.hideLoading()
            }
        })

        getRestaurants()
    }

    private fun loadRestaurants(data: RestaurantsData?) {
        if(data?.results?.any() ?: false){
            setVisibilityRefresh(false)
            val adapter = RestaurantsAdapder(data!!, ::onItemClick)
            rvRestaurants.layoutManager = LinearLayoutManager(this.activity, RecyclerView.VERTICAL, false)
            rvRestaurants.adapter = adapter
            rvRestaurants.setHasFixedSize(true)
        }
        else{
            setVisibilityRefresh(true)
        }
    }

    private fun getRestaurants() {
        viewModel.getRestaurants(app.location.latitude, app.location.longitude, 15000)
    }

    private fun onItemClick(item: Result) {


    }

    private fun setVisibilityRefresh(isVisible: Boolean){
        btnRefresh.visibility = if(isVisible) {
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}
