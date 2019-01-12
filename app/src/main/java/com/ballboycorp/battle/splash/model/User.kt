package com.ballboycorp.battle.splash.model

import com.google.firebase.auth.FirebaseUser

/**
 * Created by musooff on 12/01/2019.
 */

class User() {
    var name: String? = null
    var thumbnailUrl: String? = null
    var email: String? = null
    var phoneNumber: String? = null

    companion object {
        fun toUser(firebaseUser: FirebaseUser): User{
            val user = User()
            user.email = firebaseUser.email
            user.name = firebaseUser.displayName
            user.phoneNumber = firebaseUser.phoneNumber
            user.thumbnailUrl = firebaseUser.photoUrl.toString()
            return user
        }
    }

}