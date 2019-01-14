package com.ballboycorp.battle.battle

import com.google.firebase.firestore.FirebaseFirestore


/**
 * Created by musooff on 12/01/2019.
 */

class BattleRepository {

    companion object {
        private const val BATTLES_REF = "battles"
        private const val COUPLETS_REF = "couplets"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getCoupletsRef(battleId: String) = getBattle(battleId).collection(COUPLETS_REF)

    fun getBattle(battleId: String) = firebaseDatabase.collection(BATTLES_REF).document(battleId)

}