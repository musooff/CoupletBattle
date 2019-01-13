package com.ballboycorp.battle.default

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity

/**
 * Created by musooff on 13/01/2019.
 */

class DefaultActivity : BaseActivity(){

    companion object {
        private const val FRIEND_IDS = "friendIds"

        fun newIntent(context: Context, friendIds: Array<String>) {
            val intent = Intent(context, DefaultActivity::class.java)
            intent.putExtra(FRIEND_IDS, friendIds)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(DefaultViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}