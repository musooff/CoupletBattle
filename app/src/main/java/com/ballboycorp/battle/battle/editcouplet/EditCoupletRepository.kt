package com.ballboycorp.battle.battle.editcouplet

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 12/01/2019.
 */

class EditCoupletRepository {
    companion object {
        private const val BATTLES_REF = "battles"
        private const val COUPLETS_REF = "couplets"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getBattle(battleId: String) = firebaseDatabase.collection(BATTLES_REF).document(battleId)

    fun getCoupletsRef(battleId: String) = getBattle(battleId).collection(COUPLETS_REF)

    fun getCoupletRef(battleId: String, coupletNumber: String) = getCoupletsRef(battleId).document(coupletNumber)

}