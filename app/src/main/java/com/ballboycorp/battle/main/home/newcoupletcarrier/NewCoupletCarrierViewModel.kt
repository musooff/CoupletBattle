package com.ballboycorp.battle.main.home.newcoupletcarrier

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.home.model.CoupletCarrier
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletCarrierViewModel : ViewModel() {
    companion object {
        private const val COUPLET_CARRIER_ID = "id"
    }

    private val repository = NewCoupletCarrierRepository()

    fun saveCouplet(coupletCarrier: CoupletCarrier): Task<DocumentReference> {
        return repository.getCoupletCarriers()
                .add(coupletCarrier)
                .addOnSuccessListener {
                    it.update(mapOf(Pair(COUPLET_CARRIER_ID, it.id)))
                }
    }

    fun getServerTime() = repository.getServerTime()
}