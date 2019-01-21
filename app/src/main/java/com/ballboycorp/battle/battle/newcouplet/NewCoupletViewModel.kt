package com.ballboycorp.battle.battle.newcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletViewModel : ViewModel() {
    companion object {
        private const val COUPLET_COUNT = "coupletCount"
        private const val WRITERS_REF = "writers"
    }

    private val repository = NewCoupletRepository()

    fun saveCouplet(battle: Battle, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(battle.id!!)
                .document("${battle.id}_${battle.coupletCount}")
                .set(couplet)
                .addOnCompleteListener {
                    repository.getBattle(battle.id!!)
                            .update(mapOf(Pair(COUPLET_COUNT, (battle.coupletCount + 1))))
                    repository.getUser(couplet.creatorId!!)
                            .get()
                            .addOnSuccessListener {
                                repository.getUser(couplet.creatorId!!)
                                        .update(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(1))
                            }
                    repository.getBattle(battle.id!!)
                            .get()
                            .addOnSuccessListener {
                                val writers = it.get(WRITERS_REF) as List<*>
                                if (!writers.contains(couplet.creatorId)){
                                    repository.getBattle(battle.id!!)
                                            .update(WRITERS_REF, FieldValue.arrayUnion(couplet.creatorId))
                                }

                            }
                    repository.getAuthor(couplet.authorId!!)
                            .get()
                            .addOnSuccessListener {
                                repository.getAuthor(couplet.authorId!!)
                                        .update(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(1))
                            }
                }
    }

    fun getAuthors(): Task<QuerySnapshot> {
        return repository.getAuthors()
                .get()
    }
}