package com.ballboycorp.battle.splash

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class SplashViewModel : ViewModel() {

    companion object {

        private const val USER_ID = "id"
    }

    private val repository = SplashRepository()

    private fun saveUserCredentials(user: User, appPreference: AppPreference){
        repository.getUsersRef()
                .add(user)
                .addOnSuccessListener {
                    repository.getUsersRef().document(it.id)
                            .update(USER_ID, it.id)
                    user.id = it.id
                    appPreference.saveCredentials(user)
                }
    }

    fun updateUserCredentials(user: User, appPreference: AppPreference){
        repository.getUserRefByEmail(user.email!!)
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