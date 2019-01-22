package com.ballboycorp.battle.common.utils

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment

/**
 * Created by musooff on 22/01/2019.
 */

object ImageUtils {

    const val ACTIVITY_RESULT_THUMBNAIL = 13
    const val ACTIVITY_RESULT_COVER = 14


    fun newCameraIntentThumbnail(fragment: Fragment){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, ACTIVITY_RESULT_THUMBNAIL)
    }

    fun newCameraIntentCover(fragment: Fragment){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, ACTIVITY_RESULT_COVER)
    }
}