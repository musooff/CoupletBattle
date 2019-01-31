package com.ballboycorp.battle.main.me

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.network.StorageService
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

    private val firebaseService = FirebaseService()
    private val storageService = StorageService()

    fun getUser(userId: String): DocumentReference {
        return firebaseService.userRef(userId)
    }

    fun getImageUrl(imageUrl: String) = storageService.getImageRef(imageUrl)

    fun updateThumbnailUrl(userId: String, thumbnailUrl: String?): Task<Void> {
        return getUser(userId)
                .update(THUMBNAIL_FIELD, thumbnailUrl)
    }

    fun updateCoverUrl(userId: String, coverUrl: String?): Task<Void> {
        return getUser(userId)
                .update(COVER_FIELD, coverUrl)
    }
}