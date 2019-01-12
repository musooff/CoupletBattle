package com.ballboycorp.battle.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.home.model.CoupletCarrier

/**
 * Created by musooff on 12/01/2019.
 */

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()

    var coupletCarriers: MutableLiveData<List<CoupletCarrier>> = MutableLiveData()

    fun getCoupletCarriers(){
        repository.getCoupletCarriersRef()
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                        return@addSnapshotListener
                    }
                    if (snapshot != null){
                        coupletCarriers.postValue(CoupletCarrier.toCoupletList(snapshot.documents))
                    }
                }
    }
}