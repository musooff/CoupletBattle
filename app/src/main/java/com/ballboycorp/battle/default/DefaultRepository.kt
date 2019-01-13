package com.ballboycorp.battle.default

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 13/01/2019.
 */

class DefaultRepository {

    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletCarriersRef() = firebaseDatabase.collection(COUPLET_CARRIERS_REF)

}