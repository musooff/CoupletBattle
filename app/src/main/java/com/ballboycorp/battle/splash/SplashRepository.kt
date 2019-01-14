package com.ballboycorp.battle.splash

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class SplashRepository {

    companion object {
        private const val USERS_REF = "users"
        private const val USER_EMAIL = "email"
        private const val USER_PHONE_NUMBER = "phoneNumber"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getUsersRef() = firebaseDatabase.collection(USERS_REF)

    fun getUserRefByEmail(email: String) = firebaseDatabase.collection(USERS_REF)
            .whereEqualTo(USER_EMAIL, email)

    fun getUserRefByPhoneNumber(phoneNumber: String) = firebaseDatabase.collection(USERS_REF)
            .whereEqualTo(USER_PHONE_NUMBER, phoneNumber)

}