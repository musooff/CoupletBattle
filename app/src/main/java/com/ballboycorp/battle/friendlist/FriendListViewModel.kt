package com.ballboycorp.battle.friendlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.user.model.User
import com.google.firebase.firestore.FieldValue

/**
 * Created by musooff on 13/01/2019.
 */

class FriendListViewModel : ViewModel() {

    companion object {
        private const val FRIEND_LIST_REF = "friendList"
        private const val NOTIFICATION_ID = "id"
        private const val WRITERS = "writers"
    }

    private val repository = FriendListRepository()

    var friedList: MutableLiveData<List<User>> = MutableLiveData()


    fun getFriendList(friendIds: List<String>) {
        repository.getFriendList()
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
        repository.getFriendList()
                .document(userId)
                .get()
                .addOnSuccessListener {
                    getFriendList(it.get(FRIEND_LIST_REF) as List<String>)
                }
    }

    fun sendInvitations(friendIds: List<String>, notification: Notification) {
        friendIds.forEach {
            createNotification(it, notification)
            repository.getBattle(notification.battleId!!)
                    .update(WRITERS, FieldValue.arrayUnion(it))
        }
    }

    private fun createNotification(userId: String, notification: Notification) {
        repository.getNotificationRef(userId)
                .add(notification)
                .addOnSuccessListener {
                    val notificationId = it.id
                    repository.getNotificationRef(userId)
                            .document(notificationId)
                            .update(NOTIFICATION_ID, notificationId)
                }
    }
}