package com.ballboycorp.battle.coupletlist

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage



/**
 * Created by musooff on 12/01/2019.
 */

class CoupletListRepository {

    companion object {
        private const val COUPLET_CARRIERS_REF = "coupletCarriers"
        private const val COUPLETS_REF = "couplets"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletsRef(coupletCarrierId: String) = firebaseDatabase.collection(COUPLET_CARRIERS_REF).document(coupletCarrierId).collection(COUPLETS_REF)

}