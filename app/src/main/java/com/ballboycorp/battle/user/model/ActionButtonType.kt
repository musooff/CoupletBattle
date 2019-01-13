package com.ballboycorp.battle.user.model

import com.ballboycorp.battle.R

/**
 * Created by musooff on 14/01/2019.
 */

enum class ActionButtonType(val text: Int) {
    FRIEND_REQUEST_SENT(R.string.button_friend_request_sent),
    FRIEND_REQUEST_PENDING(R.string.button_friend_request_pending),
    MESSAGE(R.string.message_button),
    EDIT(R.string.edit_button),
    ADD_FRIEND(R.string.button_addfriend);

}