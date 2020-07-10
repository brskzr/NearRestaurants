package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.di.app
import com.brskzr.nearrestaurants.infrastructure.di.di
import com.brskzr.nearrestaurants.vm.RestaurantsViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RestaurantsFragment : Fragment() {

    @Inject
    lateinit var viewModel: RestaurantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurants, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.app.di.inject(this)
    }


}
