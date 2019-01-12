package com.ballboycorp.battle.splash

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.splash.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class SplashViewModel : ViewModel() {


    private val repository = SplashRepository()

    fun saveUserCredentials(user: User){
        repository.getUsersRef(user.email!!)
                .set(user)
    }

}