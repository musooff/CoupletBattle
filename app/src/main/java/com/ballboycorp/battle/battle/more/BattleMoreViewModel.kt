package com.ballboycorp.battle.battle.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 28/01/2019.
 */

class BattleMoreViewModel : ViewModel() {

    var requestList: MutableLiveData<List<User>> = MutableLiveData()

    fun getRequestedUsers(){
    }

}