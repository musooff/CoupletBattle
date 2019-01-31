package com.ballboycorp.battle.friendlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 13/01/2019.
 */

class FriendListViewModel : ViewModel() {

    companion object {
        private const val FRIEND_LIST_REF = "friendList"
    }

    private val firebaseService = FirebaseService()

    var friedList: MutableLiveData<List<User>> = MutableLiveData()


    fun getFriendList(friendIds: List<String>) {
        firebaseService.usersRef()
                .get()
                .addOnSuccessListener {
                    val users = User.toUserList(it.documents)
                            .filter {
                                friendIds.contains(it.id)
                            }

                    friedList.postValue(users)
                }
    }

    fun getFriendIds(userId: String){
        firebaseService.userRef(userId)
                .get()
                .addOnSuccessListener {
                    getFriendList(it.get(FRIEND_LIST_REF) as List<String>)
                }
    }

    fun sendInvitations(friendIds: List<String>, notification: Notification) {
        friendIds.forEach {
            firebaseService.addNotification(it, notification)
            firebaseService.followBattle(notification.battleId!!, it)
        }
    }
}