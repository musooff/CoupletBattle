package com.ballboycorp.battle.main.home.newbattle

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.network.FirebaseService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 12/01/2019.
 */

class NewBattleViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    fun saveCouplet(battle: Battle, notification: Notification): Task<DocumentReference> {
        return firebaseService.addBattle(battle)
                .addOnSuccessListener {
                    battle.followers
                            .filter { it != notification.fromUserId }
                            .forEach { userId ->
                                notification.battleId = it.id
                                firebaseService.addNotification(userId, notification)
                            }
                }
    }

}