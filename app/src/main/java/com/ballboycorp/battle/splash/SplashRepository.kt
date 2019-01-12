package com.ballboycorp.battle.splash

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class SplashRepository {

    companion object {
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getUsersRef(id: String) = firebaseDatabase.collection(USERS_REF).document(id)

}