package com.ballboycorp.battle.main.notification.model

/**
 * Created by musooff on 13/01/2019.
 */

enum class NotificationType(val value: String) {
    FRIEND_REQUEST("friendRequest"),
    FRIEND_ACCEPTED("friendAccepted"),
    BATTLE_JOINED("battleJoined"),
    BATTLE_INVITATION("battleInvitation");

    companion object {
        fun isBattleType(value: String) : Boolean{
            if (value == BATTLE_INVITATION.value || value == BATTLE_JOINED.value){
                return true
            }
            return false
        }
    }

}