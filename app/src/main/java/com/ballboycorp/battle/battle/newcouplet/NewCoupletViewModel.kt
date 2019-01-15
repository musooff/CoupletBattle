package com.ballboycorp.battle.battle.newcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
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

    fun saveCouplet(battleId: String, coupletsCount: Int, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(battleId)
                .document("${battleId}_$coupletsCount")
                .set(couplet)
                .addOnCompleteListener {
                    repository.getBattle(battleId)
                            .update(mapOf(Pair(COUPLET_COUNT, (coupletsCount + 1).toLong())))
                    repository.getUser(couplet.creatorId!!)
                            .get()
                            .addOnSuccessListener {
                                repository.getUser(couplet.creatorId!!)
                                        .update(mapOf(Pair(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(1))))
                            }
                    repository.getBattle(battleId)
                            .get()
                            .addOnSuccessListener {
                                val writers = it.get(WRITERS_REF) as List<*>
                                if (!writers.contains(couplet.creatorId)){
                                    repository.getBattle(battleId)
                                            .update(WRITERS_REF, FieldValue.arrayUnion(couplet.creatorId))
                                }

                            }
                }
    }

    fun getAuthors(): Task<QuerySnapshot> {
        return repository.getAuthors()
                .get()
    }
}