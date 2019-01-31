package com.ballboycorp.battle.main.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.network.FirebaseService

/**
 * Created by musooff on 13/01/2019.
 */

class NotificationViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    val notifications: MutableLiveData<List<Notification>> = MutableLiveData()

    fun getNotifications(userId: String){
        firebaseService.notificationsRef(userId)
                .get()
                .addOnSuccessListener {
                    val result = Notification.toNotificationList(it.documents)
                    notifications.postValue(result)
                }
    }
}