package com.ballboycorp.battle.coupletlist.newcouplet

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletRepository {
    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
        private const val COUPLETS_REF = "couplets"
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletCarrier(coupletCarrierId: String) = firebaseDatabase.collection(COUPLET_CARRIERS_REF).document(coupletCarrierId)

    fun getCoupletsRef(coupletCarrierId: String) = firebaseDatabase.collection(COUPLET_CARRIERS_REF).document(coupletCarrierId).collection(COUPLETS_REF)

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

}