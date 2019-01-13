package com.ballboycorp.battle.main.home.model

import com.ballboycorp.battle.R

/**
 * Created by musooff on 14/01/2019.
 */

enum class Privacy(val value: Int, val text: Int, val desc: Int) {
    PUBLIC(0, R.string.privacy_public, R.string.privacy_private_desc),
    PRIVATE(1, R.string.privacy_private, R.string.privacy_private_desc),
    SECRET(2, R.string.privacy_secret, R.string.privacy_secret_desc),
}