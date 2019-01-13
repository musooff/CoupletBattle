package com.ballboycorp.battle.main.notification

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 13/01/2019.
 */

class NotificationRepository {

    companion object {
        private const val USERS_REF = "users"
        private const val NOTIFICATIONS_REF = "notifications"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getNotifications(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)

}