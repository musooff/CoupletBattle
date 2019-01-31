package com.ballboycorp.battle.splash

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class SplashViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    private fun saveUserCredentials(user: User, appPreference: AppPreference){
        firebaseService.addUser(user)
                .addOnSuccessListener {
                    appPreference.saveCredentials(user)
                }
    }

    fun updateUserCredentials(user: User, appPreference: AppPreference){
        firebaseService.userRefByEmail(user.email!!)
                .get()
                .addOnSuccessListener {
                    if (!it.isEmpty){
                        appPreference.saveCredentials(it.documents[0].toObject(User::class.java)!!)
                    }
                    else{
                        saveUserCredentials(user, appPreference)
                    }
                }
    }

}