package com.ballboycorp.battle.coupletlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.coupletlist.model.Couplet

/**
 * Created by musooff on 12/01/2019.
 */

class CoupletListViewModel : ViewModel() {

    private val repository = CoupletListRepository()

    var couplets: MutableLiveData<List<Couplet>> = MutableLiveData()

    fun getCouplets(coupletCarrierId: String){
        repository.getCoupletsRef(coupletCarrierId)
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

}