package com.ballboycorp.battle.common.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ballboycorp.battle.R

/**
 * Created by musooff on 25/01/2019.
 */

object ButtonUtils {

    fun invalidateButton(view: View, text: String, drawableLeft: Int? = null, enabled: Boolean = false){
        view.findViewById<TextView>(R.id.button_text).text = text
        if (drawableLeft != null){
            view.findViewById<ImageView>(R.id.button_left_drawable).setImageDrawable(view.context.resources.getDrawable(drawableLeft))
        }
        else {
            view.findViewById<ImageView>(R.id.button_left_drawable).visibility = View.GONE
        }
        setEnabled(view, enabled)
    }

    fun setEnabled(view: View, enabled: Boolean){
        if (enabled) enableButton(view) else disableButton(view)
    }

    fun disableButton(view: View){
        view.isEnabled = false
    }

    fun enableButton(view: View){
        view.isEnabled = true
    }
}