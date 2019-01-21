package com.ballboycorp.battle.user.model

import com.ballboycorp.battle.R

/**
 * Created by musooff on 14/01/2019.
 */

enum class ActionButtonType(val text: Int) {
    FRIEND_REQUEST_SENT(R.string.button_friend_request_sent),
    FRIEND_REQUEST_PENDING(R.string.button_friend_request_pending),
    MESSAGE(R.string.button_message),
    ADD_FRIEND(R.string.button_add_friend);

}