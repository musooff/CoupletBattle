package com.ballboycorp.battle.friendlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 13/01/2019.
 */

class FriendListViewModel : ViewModel() {

    private val repository = FriendListRepository()

    var friedList: MutableLiveData<List<User>> = MutableLiveData()


    fun getFriendList(friendIds: List<String>) {
        repository.getFriendList()
                .get()
                .addOnSuccessListener {
                    val users = User.toUserList(it.documents)
                            .filter {
                                friendIds.contains(it.email)
                            }

                    friedList.postValue(users)
                }
    }
}