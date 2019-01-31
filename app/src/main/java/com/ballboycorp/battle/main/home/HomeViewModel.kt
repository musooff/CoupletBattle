package com.ballboycorp.battle.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class HomeViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    var authors: MutableLiveData<List<Author>> = MutableLiveData()

    var featuredBattles: MutableLiveData<List<Battle>> = MutableLiveData()
    var featuredCouplet: MutableLiveData<Couplet> = MutableLiveData()
    var topUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getAuthors() {
        firebaseService.authorsRef()
                .get()
                .addOnSuccessListener {
                    authors.postValue(Author.toAuthorList(it.documents))
                }
    }

    fun getFeaturedBattles(){
        firebaseService.featuredBattlesRef()
                .get()
                .addOnSuccessListener {
                    featuredBattles.postValue(Battle.toCoupletList(it.documents))
                }
    }

    fun getFeaturedCouplet(){
        firebaseService.battlesWithFeaturedCoupletRef()
                .get()
                .addOnSuccessListener {
                    val battle = Battle.toCoupletList(it.documents)[0]
                    firebaseService.featuredCoupletRef(battle.id!!, battle.featuredCoupletId!!)
                            .get()
                            .addOnSuccessListener {
                                featuredCouplet.postValue(Couplet.toCoupletList(it.documents)[0])

                            }
                }
    }

    fun getTopUsers(){
        firebaseService.usersRef()
                .get()
                .addOnSuccessListener {
                    topUsers.postValue(User.toUserList(it.documents))
                }
    }
}