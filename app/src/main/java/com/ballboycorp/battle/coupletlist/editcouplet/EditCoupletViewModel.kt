package com.ballboycorp.battle.coupletlist.editcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.coupletlist.model.Couplet
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

    fun getCouplet(coupletCarrierId: String, coupletNumber: String): Task<DocumentSnapshot> {
        return repository.getCoupletRef(coupletCarrierId, coupletNumber)
                .get()
    }

    fun saveCouplet(coupletCarrierId: String, coupletNumber: String, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(coupletCarrierId)
                .document(coupletNumber)
                .set(couplet)
                .addOnCompleteListener {
                    repository.getCoupletCarrier(coupletCarrierId)
                            .get()
                            .addOnSuccessListener {
                                val writers = it.get(WRITERS_REF) as List<*>
                                if (!writers.contains(couplet.creatorId)){
                                    repository.getCoupletCarrier(coupletCarrierId)
                                            .update(WRITERS_REF, FieldValue.arrayUnion(couplet.creatorId))
                                }

                            }
                }
    }
}