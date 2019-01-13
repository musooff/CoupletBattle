package com.ballboycorp.battle.main.home.newcoupletcarrier

import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.home.model.CoupletCarrier
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.user.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletCarrierViewModel : ViewModel() {
    companion object {
        private const val COUPLET_CARRIER_ID = "id"
        private const val NOTIFICATION_ID = "id"
    }

    private val repository = NewCoupletCarrierRepository()

    fun saveCouplet(coupletCarrier: CoupletCarrier, notification: Notification): Task<DocumentReference> {
        return repository.getCoupletCarriers()
                .add(coupletCarrier)
                .addOnSuccessListener {
                    it.update(mapOf(Pair(COUPLET_CARRIER_ID, it.id)))
                }
                .addOnSuccessListener {
                    coupletCarrier.writers
                            .filter { it != notification.fromUserId }
                            .forEach { userId ->
                                notification.battleId = it.id
                                createNotification(userId, notification)
                            }
                }
    }

    private fun createNotification(userId: String, notification: Notification) {
        repository.getNotificationRef(userId)
                .add(notification)
                .addOnSuccessListener {
                    val notificationId = it.id
                    repository.getNotificationRef(userId)
                            .document(notificationId)
                            .update(NOTIFICATION_ID, notificationId)
                }
    }

}