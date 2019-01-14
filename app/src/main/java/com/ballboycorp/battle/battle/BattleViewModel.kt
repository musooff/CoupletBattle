package com.ballboycorp.battle.battle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle

/**
 * Created by musooff on 12/01/2019.
 */

class BattleViewModel : ViewModel() {

    private val repository = BattleRepository()

    var couplets: MutableLiveData<List<Couplet>> = MutableLiveData()
    var battle: MutableLiveData<Battle> = MutableLiveData()

    fun getCouplets(battleId: String){
        repository.getCoupletsRef(battleId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                        return@addSnapshotListener
                    }
                    if (snapshot != null){
                        couplets.postValue(Couplet.toCoupletList(snapshot.documents))
                    }
                }
    }

    fun getBattle(battleId: String) {
        repository.getBattle(battleId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                        return@addSnapshotListener
                    }
                    if (snapshot != null){
                        battle.postValue(snapshot.toObject(Battle::class.java))
                    }
                }
    }

}