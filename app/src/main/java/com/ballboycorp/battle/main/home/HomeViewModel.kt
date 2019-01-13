package com.ballboycorp.battle.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.coupletlist.model.Couplet
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

    fun updateThumbs() = repository.getCoupletCarriersRef()
            .get()
            .addOnSuccessListener {
                val coupletCarriers = CoupletCarrier.toCoupletList(it.documents)
                coupletCarriers.forEach { ccId ->
                    repository.getCoupletCarriersRef()
                            .document(ccId.id!!)
                            .collection("couplets")
                            .get().addOnSuccessListener {
                                val couplets = Couplet.toCoupletList(it.documents)
                                couplets.forEachIndexed {index, it ->
                                    if (it.creatorId == "lannester@gmail.com"){
                                        repository.getCoupletCarriersRef()
                                                .document(ccId.id!!)
                                                .collection("couplets")
                                                .document(index.toString())
                                                .update("creatorThumbnailUrl", "https://firebasestorage.googleapis.com/v0/b/bayt-battle.appspot.com/o/user%2Fthumbnails%2Flannester%40gmail.com.png?alt=media&token=effb233b-5e8a-461a-bb8d-542d46bb2997")
                                    }
                                }
                            }
                }
            }
}