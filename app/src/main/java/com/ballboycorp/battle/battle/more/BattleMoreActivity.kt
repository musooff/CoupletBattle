package com.ballboycorp.battle.battle.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var requestAdapter: BattleJoinRequestAdapter

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(BattleMoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_more)

        customAppBar(my_appbar)
        enableBackButton()

        appPref = AppPreference.getInstance(this)
        mBattle = intent.extras!!.getSerializable(BATTLE) as Battle

        battle_description.text = mBattle.description

        requestAdapter = BattleJoinRequestAdapter(mBattle.id!!, appPref)
        requests_rv.layoutManager = LinearLayoutManager(this)
        requests_rv.adapter = requestAdapter

        viewModel.requestedWriters.observe(this, Observer {
            requests_rv_container.visibility = View.VISIBLE
            requestAdapter.submitList(it)
        })

        if (mBattle.creatorId == appPref.getUserId()){
            viewModel.getRequestedWriters(mBattle.id!!)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (mBattle.creatorId == appPref.getUserId()){
            menuInflater.inflate(R.menu.battle_more, menu)
        }
        return super.onPrepareOptionsMenu(menu)
    }
}