package com.ballboycorp.battle.battle.editcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.network.FirebaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by musooff on 12/01/2019.
 */

class EditCoupletViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    fun getCouplet(battleId: String, coupletId: String): Task<DocumentSnapshot> {
        return firebaseService.coupletRef(battleId, coupletId)
                .get()
    }

    fun saveCouplet(battleId: String, coupletNumber: String, couplet: Couplet): Task<Void>? {
        return null
    }
}