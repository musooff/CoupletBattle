package com.ballboycorp.battle.common.utils

import android.widget.LinearLayout
import kotlinx.android.synthetic.main.couplet_text.view.*
import android.view.View


/**
 * Created by musooff on 12/01/2019.
 */

object StringUtils {

    fun stylizeText(text: String?, view: LinearLayout){
        if (text == null){
            return
        }
        view.couplet_line.text = text
        view.couplet_line.viewTreeObserver.addOnGlobalLayoutListener {
            val end = view.couplet_line.getOffsetForPosition(view.couplet_line.width.toFloat(), 0f)
            if (end < text.length){
                view.couplet_line_remaining.text = text.substring(end + 1)
                return@addOnGlobalLayoutListener
            }
            view.couplet_line_remaining.visibility = View.GONE
        }
    }
}