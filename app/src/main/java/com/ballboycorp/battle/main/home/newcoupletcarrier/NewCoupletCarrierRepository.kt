package com.ballboycorp.battle.main.home.newcoupletcarrier

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletCarrierRepository {
    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
        private const val USERS_REF = "users"
        private const val NOTIFICATIONS_REF = "notifications"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getServerTime() = FieldValue.serverTimestamp()

    fun getCoupletCarriers() = firebaseDatabase.collection(COUPLET_CARRIERS_REF)


    fun getNotificationRef(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)
}