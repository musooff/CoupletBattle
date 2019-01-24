package com.ballboycorp.battle.common.preference

import android.content.Context
import android.preference.PreferenceManager
import com.ballboycorp.battle.user.model.User

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
        private const val USER_THUMB = "userThumbnail"

    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID, null)!!
    }

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, null)!!
    }

    fun getUserThumbnail(): String {
        return sharedPreferences.getString(USER_THUMB, null)!!
    }

    fun setUserThumbnail(thumbnailUrl: String) {
        editor.putString(USER_THUMB, thumbnailUrl).apply()
    }

    fun saveCredentials(user: User){
        editor.putString(USER_ID, user.id).apply()
        editor.putString(USER_NAME, user.name).apply()
        editor.putString(USER_THUMB, user.thumbnailUrl).apply()
    }

    fun getCredentials() : User{
        val user = User()
        user.id = sharedPreferences.getString(USER_ID, null)
        user.thumbnailUrl = sharedPreferences.getString(USER_THUMB, null)
        user.name = sharedPreferences.getString(USER_NAME, null)
        return user
    }

    fun signOut(){
        editor.remove(USER_ID).apply()
        editor.remove(USER_NAME).apply()
    }


}