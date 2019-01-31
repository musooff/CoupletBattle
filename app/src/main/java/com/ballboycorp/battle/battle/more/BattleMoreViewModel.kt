package com.ballboycorp.battle.battle.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 28/01/2019.
 */

class BattleMoreViewModel : ViewModel() {

    companion object {
        private const val REQUESTED_WRITERS = "requestedWriters"
        private const val NOTIFICATION_ID = "id"
        private const val ALLOWED_WRITERS = "allowedWriters"
    }

    private val repository = BattleRepository()

    var requestList: MutableLiveData<List<User>> = MutableLiveData()

    fun getRequestedUsers(){
        repository.getUsers()
    }

}