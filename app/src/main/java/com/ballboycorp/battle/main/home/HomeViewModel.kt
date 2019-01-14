package com.ballboycorp.battle.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle

/**
 * Created by musooff on 12/01/2019.
 */

class HomeViewModel : ViewModel() {

    companion object {
        private const val WRITERS = "writers"
    }

    private val repository = HomeRepository()

    var battles: MutableLiveData<List<Battle>> = MutableLiveData()

    fun getBattlesRef(isMyBattles: Boolean = false, userId: String? = null){
        if (isMyBattles){
            repository.getBattlesRef()
                    .whereArrayContains(WRITERS, userId!!)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null){
                            Log.e("ERROR", exception.message)
                            return@addSnapshotListener
                        }
                        if (snapshot != null){
                            battles.postValue(Battle.toCoupletList(snapshot.documents))
                        }
                    }
        }
        else {
            repository.getBattlesRef()
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null){
                            Log.e("ERROR", exception.message)
                            return@addSnapshotListener
                        }
                        if (snapshot != null){
                            battles.postValue(Battle.toCoupletList(snapshot.documents))
                        }
                    }
        }
    }

    fun updateThumbs() = repository.getBattlesRef()
            .get()
            .addOnSuccessListener {
                val battles = Battle.toCoupletList(it.documents)
                battles.forEach { ccId ->
                    repository.getBattlesRef()
                            .document(ccId.id!!)
                            .collection("couplets")
                            .get().addOnSuccessListener {
                                val couplets = Couplet.toCoupletList(it.documents)
                                couplets.forEachIndexed {index, it ->
                                    if (it.creatorId == "lannester@gmail.com"){
                                        repository.getBattlesRef()
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