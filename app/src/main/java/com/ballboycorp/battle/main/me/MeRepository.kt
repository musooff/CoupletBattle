package com.ballboycorp.battle.main.me

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by musooff on 13/01/2019.
 */

class MeRepository{

    companion object {
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

}