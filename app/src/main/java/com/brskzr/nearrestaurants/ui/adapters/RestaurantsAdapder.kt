package com.brskzr.nearrestaurants.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.brskzr.nearrestaurants.BuildConfig
import com.brskzr.nearrestaurants.R
import com.brskzr.nearrestaurants.data.models.RestaurantsData
import com.brskzr.nearrestaurants.data.models.Result
import com.brskzr.nearrestaurants.infrastructure.utils.gooplePhoto
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class RestaurantsAdapder(val restaurantList: MutableList<Result>, val onItemClick: (Result)->Unit) :
    RecyclerView.Adapter<RestaurantsAdapder.ViewHolder>()

{
    private var lastClickedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsAdapder.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_restaurants, parent,false)
        return RestaurantsAdapder.ViewHolder(view)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantsAdapder.ViewHolder, position: Int) {
        holder.bindItems(restaurantList[position])
        holder.view.setOnClickListener {
            lastClickedPosition = position
            onItemClick(restaurantList[position])
            restaurantList[position].isViewed = true
            notifyDataSetChanged()
        }

        val cwRow = holder.view.findViewById<CardView>(R.id.cwRow)
        val bgColor = if(restaurantList[position].isViewed)
            cwRow.context.getColor(androidx.cardview.R.color.cardview_shadow_start_color)
        else
            cwRow.context.getColor(androidx.cardview.R.color.cardview_light_background)

        cwRow.setCardBackgroundColor(bgColor)
    }

    fun removeItem(result: Result){
        val position = restaurantList.indexOf(result)
        restaurantList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun bindItems(item: Result) {
            val img = view.findViewById<ImageView>(R.id.imgRestaurant)
            val tvName = view.findViewById<TextView>(R.id.tvName)
            tvName.text = item.name
            item.photos?.firstOrNull()?.let { photo ->
                img.gooplePhoto(photo.photo_reference)
            }
        }
    }
}
