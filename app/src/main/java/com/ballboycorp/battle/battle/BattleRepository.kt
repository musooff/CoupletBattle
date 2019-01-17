package com.ballboycorp.battle.battle

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


/**
 * Created by musooff on 12/01/2019.
 */

class BattleRepository {

    companion object {
        private const val BATTLES_REF = "battles"
        private const val COUPLETS_REF = "couplets"
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    private val firebaseStorage = FirebaseStorage.getInstance()


    fun getCoupletsRef(battleId: String) = getBattle(battleId).collection(COUPLETS_REF)

    fun getBattle(battleId: String) = firebaseDatabase.collection(BATTLES_REF).document(battleId)

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

}