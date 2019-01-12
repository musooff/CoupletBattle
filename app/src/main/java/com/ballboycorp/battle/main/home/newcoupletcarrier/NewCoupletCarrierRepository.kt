package com.ballboycorp.battle.main.home.newcoupletcarrier

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletCarrierRepository {
    companion object {
        private const val COUPLET_ID = "0"
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
        private const val COUPLETS_REF = "couplets"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getServerTime() = FieldValue.serverTimestamp()

    fun getCoupletCarriers() = firebaseDatabase.collection(COUPLET_CARRIERS_REF)

}