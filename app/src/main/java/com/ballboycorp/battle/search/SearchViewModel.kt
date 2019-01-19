package com.ballboycorp.battle.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 19/01/2019.
 */

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()


    val result: MutableLiveData<List<Any>> = MutableLiveData()
    private val tempResult = ArrayList<Any>()


    fun loadDatabase() {
        repository.getBattlesRef()
                .get()
                .addOnSuccessListener {
                    tempResult.add(Battle.toCoupletList(it.documents))
                    repository.getUsers()
                            .get()
                            .addOnSuccessListener {
                                tempResult.add(User.toUserList(it.documents))
                                repository.getAuthors()
                                        .get()
                                        .addOnSuccessListener {
                                            tempResult.add(Author.toAuthorList(it.documents))
                                            result.postValue(tempResult)
                                        }
                            }
                }

    }
}