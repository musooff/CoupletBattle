package com.ballboycorp.battle.author

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 15/01/2019.
 */

class AuthorViewModel : ViewModel() {

    private val repository = AuthorRepository()

    fun getAuthor(authorId: String): DocumentReference {
        return repository.getAuthor(authorId)
    }

    fun getImageRef(imageUrl: String) = repository.getImageRef(imageUrl)
}