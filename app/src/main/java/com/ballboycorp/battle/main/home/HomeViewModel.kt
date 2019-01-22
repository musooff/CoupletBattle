package com.ballboycorp.battle.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 12/01/2019.
 */

class HomeViewModel : ViewModel() {

    companion object {
        private const val WRITERS = "writers"
        private const val COUPLETS_REF = "couplets"
        private const val COUPLET_ID = "id"
        private const val IS_FEATURED = "featured"
        private const val HAS_FEATURED_COUPLET = "hasFeaturedCouplet"
    }

    private val repository = HomeRepository()

    var battles: MutableLiveData<List<Battle>> = MutableLiveData()
    var authors: MutableLiveData<List<Author>> = MutableLiveData()

    var featuredBattles: MutableLiveData<List<Battle>> = MutableLiveData()
    var featuredCouplet: MutableLiveData<Couplet> = MutableLiveData()
    var topUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getBattlesRef(isMyBattles: Boolean = false, userId: String? = null){
        if (isMyBattles){
            repository.getBattlesRef()
                    .whereArrayContains(WRITERS, userId!!)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null){
                            Log.e("ERROR", exception.message)
                            return@addSnapshotListener
                        }
                        if (snapshot != null){
                            battles.postValue(Battle.toCoupletList(snapshot.documents))
                        }
                    }
        }
        else {
            repository.getBattlesRef()
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null){
                            Log.e("ERROR", exception.message)
                            return@addSnapshotListener
                        }
                        if (snapshot != null){
                            battles.postValue(Battle.toCoupletList(snapshot.documents))
                        }
                    }
        }
    }

    fun updateThumbs() = repository.getBattlesRef()
            .get()
            .addOnSuccessListener {
                val battles = Battle.toCoupletList(it.documents)
                battles.forEach { ccId ->
                    repository.getBattlesRef()
                            .document(ccId.id!!)
                            .collection("couplets")
                            .get().addOnSuccessListener {
                                val couplets = Couplet.toCoupletList(it.documents)
                                couplets.forEachIndexed {index, it ->
                                    if (it.creatorId == "lannester@gmail.com"){
                                        repository.getBattlesRef()
                                                .document(ccId.id!!)
                                                .collection("couplets")
                                                .document(index.toString())
                                                .update("creatorThumbnailUrl", "https://firebasestorage.googleapis.com/v0/b/bayt-battle.appspot.com/o/user%2Fthumbnails%2Flannester%40gmail.com.png?alt=media&token=effb233b-5e8a-461a-bb8d-542d46bb2997")
                                    }
                                }
                            }
                }
            }

    fun getAuthors() {
        repository.getAuthors()
                .get()
                .addOnSuccessListener {
                    authors.postValue(Author.toAuthorList(it.documents))
                }
    }

    fun getFeaturedBattles(){
        repository.getBattlesRef()
                .whereEqualTo(IS_FEATURED, true)
                .get()
                .addOnSuccessListener {
                    featuredBattles.postValue(Battle.toCoupletList(it.documents))
                }
    }

    fun getFeaturedCouplet(){
        repository.getBattlesRef()
                .whereEqualTo(HAS_FEATURED_COUPLET, true)
                .get()
                .addOnSuccessListener {
                    val battle = Battle.toCoupletList(it.documents)[0]
                    repository.getBattlesRef()
                            .document(battle.id!!)
                            .collection(COUPLETS_REF)
                            .whereEqualTo(COUPLET_ID, battle.featuredCoupletId)
                            .get()
                            .addOnSuccessListener {
                                featuredCouplet.postValue(Couplet.toCoupletList(it.documents)[0])

                            }
                }
    }

    fun getTopUsers(){
        repository.getUsers()
                .get()
                .addOnSuccessListener {
                    topUsers.postValue(User.toUserList(it.documents))
                }
    }
}