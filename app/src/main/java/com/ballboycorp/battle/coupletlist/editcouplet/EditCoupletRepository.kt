package com.ballboycorp.battle.coupletlist.editcouplet

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class EditCoupletRepository {
    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
        private const val COUPLETS_REF = "couplets"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletCarrier(coupletCarrierId: String) = firebaseDatabase.collection(COUPLET_CARRIERS_REF).document(coupletCarrierId)

    fun getCoupletsRef(coupletCarrierId: String) = firebaseDatabase.collection(COUPLET_CARRIERS_REF).document(coupletCarrierId).collection(COUPLETS_REF)

    fun getCoupletRef(coupletCarrierId: String, coupletNumber: String) = getCoupletsRef(coupletCarrierId).document(coupletNumber)

}