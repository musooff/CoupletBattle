package com.ballboycorp.battle.coupletlist.newcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.coupletlist.model.Couplet
import com.google.android.gms.tasks.Task

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletViewModel : ViewModel() {
    companion object {
        private const val COUPLET_COUNT = "coupletCount"
    }

    private val repository = NewCoupletRepository()

    fun saveCouplet(coupletCarrierId: String, coupletsCount: Int, couplet: Couplet): Task<Void> {
        return repository.getCoupletsRef(coupletCarrierId)
                .document(coupletsCount.toString())
                .set(couplet)
                .addOnCompleteListener {
                    repository.getCoupletCarrier(coupletCarrierId)
                            .update(mapOf(Pair(COUPLET_COUNT, (coupletsCount + 1).toLong())))
                }
    }
}