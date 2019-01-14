package com.ballboycorp.battle.main.home.newbattle

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class NewBattleRepository {
    companion object {
        private const val BATTLES_REF = "battles"
        private const val USERS_REF = "users"
        private const val NOTIFICATIONS_REF = "notifications"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getServerTime() = FieldValue.serverTimestamp()

    fun getBattlesRef() = firebaseDatabase.collection(BATTLES_REF)


    fun getNotificationRef(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)
}