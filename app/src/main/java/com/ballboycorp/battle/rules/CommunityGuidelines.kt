package com.ballboycorp.battle.rules

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity

/**
 * Created by musooff on 01/02/2019.
 */

class CommunityGuidelines : BaseActivity() {

    companion object {
        fun newIntent(context: Context){
            val intent = Intent(context, CommunityGuidelines::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_guidelines)

        setTitle(getString(R.string.title_community_guidelines))
        enableBackButton(true)
    }
}