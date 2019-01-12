package com.ballboycorp.battle.user

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.user.model.User
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 13/01/2019.
 */

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    fun getUser(userId: String): DocumentReference {
        return repository.getUser(userId)
    }
}