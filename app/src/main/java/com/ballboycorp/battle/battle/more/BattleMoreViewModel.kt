package com.ballboycorp.battle.battle.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 28/01/2019.
 */

class BattleMoreViewModel : ViewModel() {

    companion object {
        private const val REQUESTED_WRITERS = "requestedWriters"
    }

    private val firebaseService = FirebaseService()

    var requestedWriters: MutableLiveData<List<User>> = MutableLiveData()

    fun getRequestedWriters(battleId: String){
        firebaseService.battleRef(battleId)
                .get()
                .addOnSuccessListener {
                    val requestedWriterIds = it.get(REQUESTED_WRITERS) as  List<String>
                    firebaseService.usersRef()
                            .get()
                            .addOnSuccessListener {
                                val users = User.toUserList(it.documents)
                                        .filter {
                                            requestedWriterIds.contains(it.id)
                                        }
                                requestedWriters.postValue(users)
                            }
                }
    }

}