package com.ballboycorp.battle.author

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.network.StorageService
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 15/01/2019.
 */

class AuthorViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    private val storageService = StorageService()

    fun getAuthor(authorId: String): DocumentReference {
        return firebaseService.authorRef(authorId)
    }

    fun getImageRef(imageUrl: String) = storageService.getImageRef(imageUrl)
}