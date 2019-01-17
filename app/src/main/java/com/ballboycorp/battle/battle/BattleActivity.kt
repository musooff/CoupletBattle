package com.ballboycorp.battle.battle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.invite.InviteActivity
import com.ballboycorp.battle.battle.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.battle.utils.FriendUtil
import com.ballboycorp.battle.main.home.model.Battle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.android.synthetic.main.battle_header.*
import kotlinx.android.synthetic.main.battle_header.view.*

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
            battle_name.text = it.name
            battle_count.text = String.format(getString(R.string.battle_couplet_count_format), it.coupletCount)
        })

        viewModel.friends.observe(this, Observer {
            friends_in_tv.text = String.format(getString(R.string.friend_count_format), it.size)
            friends_in_tv.setCompoundDrawables(null, null, null, null)

            FriendUtil.updateFriendThumb(it, battle_header)
        })


        viewModel.getBattle(battleId, appPreff.getUserId())
        viewModel.getCouplets(battleId)

    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.battle, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_info -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}