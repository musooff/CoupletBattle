package com.ballboycorp.battle.friendlist

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 13/01/2019.
 */

class FriendListRepository {

    companion object {
        private const val USERS_REF = "users"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun getFriendList() = firebaseDatabase.collection(USERS_REF)

}