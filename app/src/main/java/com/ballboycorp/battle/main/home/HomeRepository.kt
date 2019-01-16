package com.ballboycorp.battle.main.home

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class HomeRepository {

    companion object {
        private const val BATTLES_REF = "battles"
        private const val AUTHORS_REF = "authors"
        private const val USERS_REF = "users"

    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getBattlesRef() = firebaseDatabase.collection(BATTLES_REF)

    fun getAuthors() = firebaseDatabase.collection(AUTHORS_REF)

    fun getUsers() = firebaseDatabase.collection(USERS_REF)

}