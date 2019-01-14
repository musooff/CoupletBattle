package com.ballboycorp.battle.friendlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 13/01/2019.
 */

class FriendListViewModel : ViewModel() {

    companion object {
        private const val FRIEND_LIST_REF = "friendList"
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
}