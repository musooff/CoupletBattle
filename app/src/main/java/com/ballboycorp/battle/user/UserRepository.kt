package com.ballboycorp.battle.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by musooff on 13/01/2019.
 */

class UserRepository {

    companion object {
        private const val USERS_REF = "users"
        private const val BATTLES_REF = "battles"
        private const val COUPLETS_REF = "couplets"
        private const val NOTIFICATIONS_REF = "notifications"
        private const val CREATOR_FIELD = "creatorId"
    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getUser(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId)

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

    fun getNotificationRef(userId: String) = firebaseDatabase.collection(USERS_REF).document(userId).collection(NOTIFICATIONS_REF)

    fun getBattles() = firebaseDatabase.collection(BATTLES_REF)

    fun getCouplets(battleId: String, creatorId: String) = getBattles().document(battleId).collection(COUPLETS_REF).whereEqualTo(CREATOR_FIELD, creatorId)

}