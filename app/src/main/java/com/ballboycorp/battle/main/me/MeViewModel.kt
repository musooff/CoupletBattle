package com.ballboycorp.battle.main.me

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 13/01/2019.
 */

class MeViewModel : ViewModel(){

    private val repository = MeRepository()

    fun getUser(userId: String): DocumentReference {
        return repository.getUser(userId)
    }

    fun getImageUrl(imageUrl: String) = repository.getImageRef(imageUrl)
}