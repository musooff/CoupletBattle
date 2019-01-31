package com.ballboycorp.battle.network

import com.google.firebase.storage.FirebaseStorage

/**
 * Created by musooff on 31/01/2019.
 */

class StorageService {

    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getImageRef(imageUrl: String) = firebaseStorage.getReference(imageUrl)

}