package com.ballboycorp.battle.common.utils

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ballboycorp.battle.R
import kotlinx.android.synthetic.main.couplet_text.view.*
import android.text.StaticLayout
import android.graphics.Paint.Align
import android.text.Layout
import android.text.TextPaint




/**
 * Created by musooff on 12/01/2019.
 */

object StringUtils {
    fun increaseFontSizeForFirstAndLast(text: String?): SpannableString? {
        if (text == null){
            return null
        }
        val spannable = SpannableString(text)
        spannable.setSpan(RelativeSizeSpan(1.5f), 0, 1, 0)
        spannable.setSpan(RelativeSizeSpan(1.5f), text.length - 1, text.length, 0)
        return spannable
    }

    fun stylizeText(text: String?, view: LinearLayout){
        view.couplet_line.text = text
        return
        if (isSingleLine(text, view.couplet_line)){
            view.couplet_line.text = text
            return
        }

        view.couplet_line.text = text?.substring(0, 10)
        val remaining = TextView(view.context)
        remaining.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        remaining.gravity = Gravity.END
        remaining.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
        remaining.setTextColor(view.context.resources.getColor(R.color.colorBlack))
        view.addView(remaining)
        remaining.text = text?.substring(11)

    }

    private fun isSingleLine(text: String?, textView: TextView) : Boolean{
        val textPaint = TextPaint()
        textPaint.typeface = textView.typeface
        textPaint.textSize = textView.textSize
        val staticLayout = StaticLayout(text, textPaint, textView.width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
        return 1 == staticLayout.lineCount
    }

    private fun getRemainingLines(text: String?, textView: TextView): String? {
        val textPaint = TextPaint()
        textPaint.typeface = textView.typeface
        textPaint.textSize = textView.textSize
        val staticLayout = StaticLayout(text, textPaint, textView.width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
        staticLayout.getLineForOffset(1)
        return null
    }
}