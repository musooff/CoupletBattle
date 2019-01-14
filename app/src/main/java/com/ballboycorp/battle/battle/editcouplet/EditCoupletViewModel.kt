package com.ballboycorp.battle.battle.editcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue

/**
 * Created by musooff on 12/01/2019.
 */

class EditCoupletViewModel : ViewModel() {
    companion object {
        private const val WRITERS_REF = "writers"
    }

    private val repository = EditCoupletRepository()

    fun getCouplet(battleId: String, coupletNumber: String): Task<DocumentSnapshot> {
        return repository.getCoupletRef(battleId, coupletNumber)
                .get()
    }

    fun saveCouplet(battleId: String, coupletNumber: String, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(battleId)
                .document(coupletNumber)
                .set(couplet)
                .addOnCompleteListener {
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
}