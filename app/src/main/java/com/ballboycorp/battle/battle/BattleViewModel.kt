package com.ballboycorp.battle.battle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.common.utils.addIfNotExists
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User
/**
 * Created by musooff on 12/01/2019.
 */

class BattleViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    var couplets: MutableLiveData<List<Couplet>> = MutableLiveData()
    var battle: MutableLiveData<Battle> = MutableLiveData()
    var friends = MutableLiveData<List<User>>()


    fun getCouplets(battleId: String){
        firebaseService.coupletsRef(battleId)
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
        firebaseService.userRef(userId)
                .get()
                .addOnSuccessListener {
                    val userRemote = it.toObject(User::class.java)
                    userRemote!!.friendList
                            .filter {
                                writers.contains(it)
                            }
                            .take(3)
                            .forEach {
                                firebaseService.userRef(userId)
                                        .get()
                                        .addOnSuccessListener {
                                            val friendRemote = it.toObject(User::class.java)
                                            friends.addIfNotExists(friendRemote!!)
                                        }
                            }
                }
    }

    fun getBattle(battleId: String, userId: String) {
        firebaseService.battleRef(battleId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                        return@addSnapshotListener
                    }
                    if (snapshot != null){
                        val battleRemote = snapshot.toObject(Battle::class.java)
                        battle.postValue(battleRemote)
                        getFriends(battleRemote!!.followers, userId)

                    }
                }
    }

    fun followBattle(battleId: String, userId: String){
        firebaseService.followBattle(battleId, userId)
    }

    fun unFollowBattle(battleId: String, userId: String){
        firebaseService.unFollowBattle(battleId, userId)
    }

    fun requestJoin(battle: Battle, userId: String, notification: Notification){
        firebaseService.requestJoinBattle(battle.id!!, userId)
        firebaseService.addNotification(battle.creatorId!!, notification)
    }
}