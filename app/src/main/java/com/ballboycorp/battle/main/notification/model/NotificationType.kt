package com.ballboycorp.battle.main.notification.model

/**
 * Created by musooff on 13/01/2019.
 */

enum class NotificationType(typeCode: Int, val value: String) {
    FRIEND_REQUEST(0, "friendRequest"),
    FRIEND_ACCEPTED(1, "friendAccepted"),
    BATTLE_JOINED(1, "battleJoined")
}