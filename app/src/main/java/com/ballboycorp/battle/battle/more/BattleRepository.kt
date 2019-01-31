package com.ballboycorp.battle.battle.more

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 28/01/2019.
 */

class BattleRepository {


    companion object {
        private const val USERS_REF = "users"
        private const val BATTLES_REF = "battles"
        private const val NOTIFICATIONS_REF = "notifications"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getUsers() = firebaseDatabase.collection(USERS_REF)

    fun getBattle(battleId: String) = firebaseDatabase.collection(BATTLES_REF).document(battleId)

    fun getNotificationRef(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)
}