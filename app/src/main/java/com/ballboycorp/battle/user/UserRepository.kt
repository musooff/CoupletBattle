package com.ballboycorp.battle.user

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 13/01/2019.
 */

class UserRepository {

    companion object {
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

}