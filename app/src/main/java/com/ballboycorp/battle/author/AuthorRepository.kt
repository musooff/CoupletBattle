package com.ballboycorp.battle.author

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by musooff on 15/01/2019.
 */

class AuthorRepository {

    companion object {
        private const val AUTHORS_REF = "authors"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getAuthor(authorId: String) = firebaseDatabase.collection(AUTHORS_REF).document(authorId)

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

}