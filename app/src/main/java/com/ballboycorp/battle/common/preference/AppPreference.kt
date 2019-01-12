package com.ballboycorp.battle.common.preference

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by musooff on 12/01/2019.
 */

class AppPreference(context: Context) {

    companion object {
        private var INSTANCE: AppPreference? = null

        fun getIntance(context: Context) =
                INSTANCE ?: AppPreference(context)
                        .also {
                            INSTANCE = it
                        }
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun getUserId(): String {
        return "lannester"
    }

    fun getUserFullname(): String {
        return "Tyrion Lannester"
    }

    fun getSecondaryUserId(): String {
        return "targerian"
    }

    fun getSecondaryUserFullname(): String {
        return "Aegon Targaryan"
    }


}