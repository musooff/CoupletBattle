package com.ballboycorp.battle.battle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.common.utils.add
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class BattleViewModel : ViewModel() {

    private val repository = BattleRepository()

    var couplets: MutableLiveData<List<Couplet>> = MutableLiveData()
    var battle: MutableLiveData<Battle> = MutableLiveData()
    var friends = MutableLiveData<List<User>>()


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

    private fun getFriends(writers: List<String>, userId: String){
        repository.getUser(userId)
                .get()
                .addOnSuccessListener {
                    val userRemote = it.toObject(User::class.java)
                    userRemote!!.friendList
                            .filter {
                                writers.contains(it)
                            }
                            .take(3)
                            .forEach {
                                repository.getUser(it)
                                        .get()
                                        .addOnSuccessListener {
                                            val friendRemote = it.toObject(User::class.java)
                                            friends.add(friendRemote!!)
                                        }
                            }
                }
    }

    fun getBattle(battleId: String, userId: String) {
        repository.getBattle(battleId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                        return@addSnapshotListener
                    }
                    if (snapshot != null){
                        val battleRemote = snapshot.toObject(Battle::class.java)
                        battle.postValue(battleRemote)
                        getFriends(battleRemote!!.writers, userId)

                    }
                }
    }

    fun getImageUrl(imageUrl: String) = repository.getImageRef(imageUrl)


}