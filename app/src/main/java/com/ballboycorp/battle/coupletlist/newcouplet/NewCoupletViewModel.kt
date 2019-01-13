package com.ballboycorp.battle.coupletlist.newcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.coupletlist.model.Couplet
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletViewModel : ViewModel() {
    companion object {
        private const val COUPLET_COUNT = "coupletCount"
        private const val WRITERS_REF = "writers"
    }

    private val repository = NewCoupletRepository()

    fun saveCouplet(coupletCarrierId: String, coupletsCount: Int, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(coupletCarrierId)
                .document("${coupletCarrierId}_$coupletsCount")
                .set(couplet)
                .addOnCompleteListener {
                    repository.getCoupletCarrier(coupletCarrierId)
                            .update(mapOf(Pair(COUPLET_COUNT, (coupletsCount + 1).toLong())))
                    repository.getUser(couplet.creatorId!!)
                            .get()
                            .addOnSuccessListener {
                                repository.getUser(couplet.creatorId!!)
                                        .update(mapOf(Pair(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(1))))
                            }
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