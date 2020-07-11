package com.brskzr.nearrestaurants.infrastructure.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.brskzr.nearrestaurants.R

typealias DialogHandler = () -> Unit

object DialogUtils {

    fun alert(context: Context,
              title: String,
              message:String,
              onOkey: DialogHandler? = null,
              onCancel:DialogHandler? = null){

        val builder = AlertDialog.Builder(context, R.style.AlertDialog).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.resources.getString(R.string.okey),  object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        onOkey?.invoke()
                    }
                })
            setNegativeButton(context.resources.getString(R.string.cancel), object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        onCancel?.invoke()
                    }
                })
        }.show()
    }

}