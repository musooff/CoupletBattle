package com.ballboycorp.battle.common.preference

import android.content.Context
import android.preference.PreferenceManager
import com.ballboycorp.battle.splash.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class AppPreference(context: Context) {

    companion object {


        private var INSTANCE: AppPreference? = null

        fun getInstance(context: Context) =
                INSTANCE ?: AppPreference(context)
                        .also {
                            INSTANCE = it
                        }

        private const val USER_ID = "userId"
        private const val USER_NAME = "userName"

    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID, null)!!
    }

    fun getUserFullname(): String {
        return sharedPreferences.getString(USER_NAME, null)!!
    }

    fun getSecondaryUserId(): String {
        return "targerian"
    }

    fun getSecondaryUserFullname(): String {
        return "Aegon Targaryan"
    }
    fun saveCredentials(user: User){
        editor.putString(USER_ID, user.email).apply()
        editor.putString(USER_NAME, user.name).apply()
    }


}