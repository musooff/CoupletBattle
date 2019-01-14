package com.ballboycorp.battle.main.home

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class HomeRepository {

    companion object {
        private const val BATTLES_REF = "battles"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getBattlesRef() = firebaseDatabase.collection(BATTLES_REF)
}