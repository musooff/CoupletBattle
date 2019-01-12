package com.ballboycorp.battle.common.utils

import android.text.SpannableString
import android.text.style.RelativeSizeSpan

/**
 * Created by musooff on 12/01/2019.
 */

class StringUtils {
    fun increaseFontSizeForFirstAndLast(text: String?): SpannableString? {
        if (text == null){
            return null
        }
        val spannable = SpannableString(text)
        spannable.setSpan(RelativeSizeSpan(1.5f), 0, 1, 0)
        spannable.setSpan(RelativeSizeSpan(1.5f), text.length - 1, text.length, 0)
        return spannable
    }
}