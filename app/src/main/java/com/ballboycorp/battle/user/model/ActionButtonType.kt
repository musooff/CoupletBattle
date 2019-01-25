package com.ballboycorp.battle.user.model

import com.ballboycorp.battle.R

/**
 * Created by musooff on 14/01/2019.
 */

enum class ActionButtonType(val text: Int, val drawableLeft: Int) {
    FRIEND_REQUEST_SENT(R.string.button_friend_request_sent, R.drawable.ic_person_sent_white_24dp),
    FRIEND_REQUEST_PENDING(R.string.button_friend_request_pending, R.drawable.ic_person_add_white_24dp),
    MESSAGE(R.string.button_message, R.drawable.ic_chat_white_24dp),
    ADD_FRIEND(R.string.button_add_friend, R.drawable.ic_person_add_white_24dp);

}