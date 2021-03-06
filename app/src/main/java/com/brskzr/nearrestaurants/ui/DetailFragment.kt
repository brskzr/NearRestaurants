package com.brskzr.nearrestaurants.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.infrastructure.utils.DialogUtils
import com.brskzr.nearrestaurants.infrastructure.utils.gooplePhoto
import com.brskzr.nearrestaurants.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*

class DetailFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    val parent : MainActivity
        get() = requireActivity() as MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = parent.viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener { parent.onBackPressed() }
        btnDelete.setOnClickListener { deleteConfirmation() }

        viewModel.resultOfDetail?.let {
            ivRestaurantName.infoText = it.name
            ivRating.infoText = "${it.rating}/5"
            ivUserRatingTotal.infoText = it.user_ratings_total.toString()
            ivVicinity.infoText = it.vicinity

            it.photos?.firstOrNull()?.let { photo ->
                imgRestaurant.gooplePhoto(photo.photo_reference)
            }
        }
    }

    private fun deleteConfirmation() {
        DialogUtils.alert(requireContext(),
            getString(R.string.Confirm),
            getString(R.string.DoYouWantToDelete),
            onOkey = {
                delete()
            },
            onCancel = { })
    }

    private fun delete(){
        viewModel.resultOfDetail?.let {
            viewModel.deleteTemporaryRestaurant(it)
            viewModel.resultOfDetail = null
        }

        parent.onBackPressed()
    }
}
