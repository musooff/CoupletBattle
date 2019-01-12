package com.ballboycorp.battle.main.home

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class HomeRepository {

    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletCarriersRef() = firebaseDatabase.collection(COUPLET_CARRIERS_REF)
}