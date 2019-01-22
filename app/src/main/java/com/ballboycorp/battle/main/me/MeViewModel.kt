package com.ballboycorp.battle.main.me

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 13/01/2019.
 */

class MeViewModel : ViewModel(){

    companion object {
        private const val THUMBNAIL_FIELD = "thumbnailUrl"
        private const val COVER_FIELD = "coverUrl"
    }

    private val repository = MeRepository()

    fun getUser(userId: String): DocumentReference {
        return repository.getUser(userId)
    }

    fun getImageUrl(imageUrl: String) = repository.getImageRef(imageUrl)

    fun updateThumbnailUrl(userId: String, thumbnailUrl: String?): Task<Void> {
        return getUser(userId)
                .update(THUMBNAIL_FIELD, thumbnailUrl)
    }

    fun updateCoverUrl(userId: String, coverUrl: String?): Task<Void> {
        return getUser(userId)
                .update(COVER_FIELD, coverUrl)
    }
}