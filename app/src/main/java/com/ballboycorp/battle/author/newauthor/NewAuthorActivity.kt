package com.ballboycorp.battle.author.newauthor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity

/**
 * Created by musooff on 15/01/2019.
 */

class NewAuthorActivity : BaseActivity() {

    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, NewAuthorActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_author)
    }
}