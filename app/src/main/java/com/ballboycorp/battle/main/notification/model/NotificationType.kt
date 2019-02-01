package com.ballboycorp.battle.main.notification.model

/**
 * Created by musooff on 13/01/2019.
 */

enum class NotificationType(val value: String, val isBattleType: Boolean) {
    FRIEND_REQUEST("friendRequest", false),
    FRIEND_ACCEPTED("friendAccepted", false),
    BATTLE_JOINED("battleJoined", true),
    BATTLE_JOIN_REQUEST("battleJoinRequest", true),
    BATTLE_JOIN_CONFIRMED("battleJoinConfirmed", true),
    BATTLE_JOIN_REJECTED("battleJoinRejected", true),
    BATTLE_INVITATION("battleInvitation", true);


    companion object {
        fun isBattleType(value: String) : Boolean {
            NotificationType.values().forEach {
                if (it.value == value){
                    return it.isBattleType
                }
            }
            return false
        }
    }

}