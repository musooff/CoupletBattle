package com.ballboycorp.battle.splash

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class SplashViewModel : ViewModel() {


    private val repository = SplashRepository()

    private fun saveUserCredentials(user: User){
        repository.getUsersRef(user.email!!)
                .set(user)
    }

    fun updateUserCredentials(user: User, appPreference: AppPreference){
        repository.getUsersRef(user.email!!)
                .get()
                .addOnSuccessListener {
                    if (it.exists()){
                        appPreference.saveCredentials(it.toObject(User::class.java)!!)
                    }
                    else{
                        saveUserCredentials(user)
                        appPreference.saveCredentials(user)
                    }
                }
    }

}