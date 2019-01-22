package com.ballboycorp.battle.user.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by musooff on 12/01/2019.
 */

class User() {
    var id: String? = null
    var name: String? = null
    var thumbnailUrl: String? = null
    var coverUrl: String? = null
    var email: String? = null
    var phoneNumber: String? = null
    var coupletCount: Int = 0
    var friendCount: Int = 0
    var friendList: List<String> = arrayListOf()
    var friendsPendingTo: List<String> = arrayListOf()
    var friendsPendingFrom: List<String> = arrayListOf()


    companion object {
        fun toUser(firebaseUser: FirebaseUser): User{
            val user = User()
            user.email = firebaseUser.email
            user.name = firebaseUser.displayName
            user.phoneNumber = firebaseUser.phoneNumber
            user.coverUrl = "users/covers/user_default_cover.jpg"
            user.thumbnailUrl = "users/thumbnails/user_default_thumb.png"
            return user
        }

        fun toUserList(documents: List<DocumentSnapshot>): List<User>{
            val result = ArrayList<User>()
            documents.forEach {
                val user = it.toObject(User::class.java)
                result.add(user!!)
            }
            return result
        }
    }

}