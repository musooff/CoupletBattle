package com.ballboycorp.battle.main.notification.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Created by musooff on 13/01/2019.
 */

class Notification() {
    var id: String? = null
    @ServerTimestamp
    var createdTime: Date? = null
    var fromUser: String? = null
    var fromUserId: String? = null
    var type: String? = null
    var notificationThumbUrl: String? = null
    var battleId: String? = null

    companion object {
        fun toNotificationList(documents: List<DocumentSnapshot>): List<Notification>{
            val result = ArrayList<Notification>()
            documents.forEach {
                val notification = it.toObject(Notification::class.java)
                result.add(notification!!)
            }
            return result
        }
    }
}