package com.ballboycorp.battle.battle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.invite.InviteActivity
import com.ballboycorp.battle.battle.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.main.home.model.Battle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_battle.*

/**
 * Created by musooff on 12/01/2019.
 */

class BattleActivity : BaseActivity() {

    companion object {
        private const val BATTLE_ID = "battleId"

        fun newIntent(context: Context, battleId: String) {
            val intent = Intent(context, BattleActivity::class.java)
            intent.putExtra(BATTLE_ID, battleId)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(BattleViewModel::class.java)
    }

    private lateinit var adapter: BattleAdapter
    private lateinit var battleId: String
    private lateinit var mBattle: Battle
    private var coupletsCount: Int = 0
    private lateinit var lastPostedUserId: String
    private lateinit var startingLetter: String
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        customAppBar(my_appbar)


        appPreff = AppPreference.getInstance(this)

        battleId = intent.extras!!.getString(BATTLE_ID)!!

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        battle_rv.layoutManager = layoutManager

        adapter = BattleAdapter()
        battle_rv.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        battle_rv.addItemDecoration(dividerItemDecoration)


        couplet_add.setOnClickListener {
            if (lastPostedUserId == appPreff.getUserId()){
                Snackbar.make(couplet_add, getString(R.string.not_your_turn), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.action_invite)) {
                            InviteActivity.newIntent(this, mBattle)
                        }
                        .show()
            }
            else {
                NewCoupletActivity.newIntent(this, battleId, coupletsCount, startingLetter, false)
            }
        }

        viewModel.couplets.observe(this, Observer {
            coupletsCount = it.size
            lastPostedUserId = it[coupletsCount - 1].creatorId!!
            startingLetter = it[coupletsCount - 1].endingLetter!!
            adapter.submitList(it)
            battle_rv.smoothScrollToPosition(adapter.itemCount - 1)

        })

        viewModel.battle.observe(this, Observer {
            mBattle = it
            my_collapsing.title = it.name
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getBattle(battleId)
        viewModel.getCouplets(battleId)
    }
}