package com.ballboycorp.battle.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by musooff on 13/01/2019.
 */

class UserRepository {

    companion object {
        private const val USERS_REF = "users"
        private const val NOTIFICATIONS_REF = "notifications"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

    fun getNotificationRef(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)

}