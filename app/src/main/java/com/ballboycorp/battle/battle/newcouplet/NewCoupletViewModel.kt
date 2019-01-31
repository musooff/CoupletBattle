package com.ballboycorp.battle.battle.newcouplet

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.network.FirebaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletViewModel : ViewModel() {


    private val firebaseService = FirebaseService()


    fun saveCouplet(battle: Battle, couplet: Couplet): Task<Void> {
        return firebaseService.addCouplet(battle, couplet)
    }

    fun getAuthors(): Task<QuerySnapshot> {
        return firebaseService.authorsRef()
                .get()
    }
}