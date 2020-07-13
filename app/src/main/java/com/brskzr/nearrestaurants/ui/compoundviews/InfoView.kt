package com.brskzr.nearrestaurants.ui.compoundviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.brskzr.nearrestaurants.R
import kotlinx.android.synthetic.main.layout_info_view.view.*

class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var labelText: String
        get() = tvLabel.text.toString()
        set(value) {
            tvLabel.text = value
        }

    var infoText: String
        get() = tvInfo.text.toString()
        set(value) {
            tvInfo.text = value
        }


    init {
        LayoutInflater.from(this.context).inflate(R.layout.layout_info_view, this, true)
        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.InfoView, 0, 0)
            val labelText = (typedArray.getText(R.styleable.InfoView_label_text) ?: "").toString()
            val infoText = (typedArray.getText(R.styleable.InfoView_info_text) ?: "").toString()

            tvLabel.text = labelText
            tvInfo.text = infoText

            typedArray.recycle()
        }
    }



}

