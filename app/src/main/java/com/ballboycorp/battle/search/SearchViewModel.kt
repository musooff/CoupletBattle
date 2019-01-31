package com.ballboycorp.battle.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 19/01/2019.
 */

class SearchViewModel : ViewModel() {


    private val firebaseService = FirebaseService()

    val result: MutableLiveData<List<Any>> = MutableLiveData()
    private val tempResult = ArrayList<Any>()


    fun loadDatabase() {
        firebaseService.battlesRef()
                .get()
                .addOnSuccessListener {
                    tempResult.add(Battle.toCoupletList(it.documents))
                    firebaseService.usersRef()
                            .get()
                            .addOnSuccessListener {
                                tempResult.add(User.toUserList(it.documents))
                                firebaseService.authorsRef()
                                        .get()
                                        .addOnSuccessListener {
                                            tempResult.add(Author.toAuthorList(it.documents))
                                            result.postValue(tempResult)
                                        }
                            }
                }

    }
}