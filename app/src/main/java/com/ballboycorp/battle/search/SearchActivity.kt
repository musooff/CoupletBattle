package com.ballboycorp.battle.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by musooff on 17/01/2019.
 */

class SearchActivity : BaseActivity(){

    companion object {
        fun newIntent(context: Context){
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search.setOnCloseListener {
            this@SearchActivity.finish()
            true
        }


    }
}