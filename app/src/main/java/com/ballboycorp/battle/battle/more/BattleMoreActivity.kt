package com.ballboycorp.battle.battle.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.main.home.model.Battle
import kotlinx.android.synthetic.main.activity_battle_more.*

/**
 * Created by musooff on 25/01/2019.
 */

class BattleMoreActivity : BaseActivity(){

    companion object {

        private const val BATTLE = "battle"

        fun newIntent(context: Context, battle: Battle){
            val intent = Intent(context, BattleMoreActivity::class.java)
            intent.putExtra(BATTLE, battle)
            context.startActivity(intent)
        }
    }

    private lateinit var mBattle: Battle
    private lateinit var appPref: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_more)

        customAppBar(my_appbar)
        enableBackButton()

        appPref = AppPreference.getInstance(this)
        mBattle = intent.extras!!.getSerializable(BATTLE) as Battle

        battle_description.text = mBattle.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.battle_more, menu)
        return super.onCreateOptionsMenu(menu)
    }
}