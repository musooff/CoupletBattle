package com.ballboycorp.battle.main.mybattles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.network.FirebaseService

/**
 * Created by musooff on 31/01/2019.
 */

class MyBattlesViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    var battles: MutableLiveData<List<Battle>> = MutableLiveData()


    fun getMyBattles(userId: String){
        firebaseService.myBattlersRef(userId)
                .get()
                .addOnSuccessListener {
                    battles.postValue(Battle.toCoupletList(it.documents))

                }
    }
}