package com.ballboycorp.battle.default

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 13/01/2019.
 */

class DefaultRepository {

    companion object {
        private const val BATTLES_REF = "battles"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getBattlesRef() = firebaseDatabase.collection(BATTLES_REF)

}