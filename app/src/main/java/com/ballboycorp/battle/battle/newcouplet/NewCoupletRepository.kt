package com.ballboycorp.battle.battle.newcouplet

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletRepository {
    companion object {
        private const val BATTLES_REF = "battles"
        private const val COUPLETS_REF = "couplets"
        private const val USERS_REF = "users"
        private const val AUTHORS_REF = "authors"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getBattle(battleId: String) = firebaseDatabase.collection(BATTLES_REF).document(battleId)

    fun getCoupletsRef(battleId: String) = getBattle(battleId).collection(COUPLETS_REF)

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

    fun getAuthors() = firebaseDatabase.collection(AUTHORS_REF).document()

}