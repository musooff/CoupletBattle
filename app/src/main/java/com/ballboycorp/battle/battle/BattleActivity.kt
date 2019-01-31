package com.ballboycorp.battle.battle

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.invite.InviteActivity
import com.ballboycorp.battle.battle.more.BattleMoreActivity
import com.ballboycorp.battle.battle.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.battle.utils.FriendUtil
import com.ballboycorp.battle.common.utils.ButtonUtils
import com.ballboycorp.battle.friendlist.FriendListActivity
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.home.model.Privacy
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.main.notification.model.NotificationType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.android.synthetic.main.battle_header.*
import kotlinx.android.synthetic.main.empty_list.*

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
    private var startingLetter: String? = null
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        customAppBar(my_appbar)
        enableBackButton()


        appPreff = AppPreference.getInstance(this)

        battleId = intent.extras!!.getString(BATTLE_ID)!!

        val layoutManager = LinearLayoutManager(this)
        battle_rv.layoutManager = layoutManager

        adapter = BattleAdapter()
        battle_rv.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        battle_rv.addItemDecoration(dividerItemDecoration)

        viewModel.couplets.observe(this, Observer {
            coupletsCount = it.size
            if (coupletsCount == 0) {
                empty_list.visibility = View.VISIBLE
                empty_text.text = getString(R.string.empty_battles)
            }
            else {
                empty_list.visibility = View.GONE
                lastPostedUserId = it[coupletsCount - 1].creatorId!!
                startingLetter = it[coupletsCount - 1].endingLetter!!
                adapter.submitList(it)
                battle_rv.smoothScrollToPosition(adapter.itemCount - 1)
            }

        })

        viewModel.battle.observe(this, Observer {
            mBattle = it
            invalidateOptionsMenu()
            battle_name.text = it.name
            battle_count.text = String.format(getString(R.string.battle_couplet_count_format), it.coupletCount)

            container.setOnClickListener {
                InviteActivity.newIntent(this, mBattle)
            }
        })

        viewModel.friends.observe(this, Observer {
            friends_in_tv.text = String.format(getString(R.string.friend_count_format), it.size)
            friends_in_tv.setCompoundDrawables(null, null, null, null)

            FriendUtil.updateFriendThumb(it, battle_header)

            container.setOnClickListener { view ->
                val friendIds = it.map { it.id!! }
                FriendListActivity.newIntent(this, appPreff.getUserId(), friendIds.toTypedArray())
            }
        })


        viewModel.getBattle(battleId, appPreff.getUserId())
        viewModel.getCouplets(battleId)

        ButtonUtils.invalidateButton(button_new_couplet, getString(R.string.new_couplet), R.drawable.ic_add_white_24dp, true)
        button_new_couplet.setOnClickListener {
            newCouplet(it)
        }

    }

    private fun newCouplet(view: View){
        if (!::mBattle.isInitialized){
            return
        }
        if (mBattle.privacy == Privacy.PRIVATE.text && !mBattle.allowedWriters.contains(appPreff.getUserId())){
            val alert = AlertDialog.Builder(this)
                    .setTitle("Байтбараки пушида")

            if (mBattle.requestedWriters.contains(appPreff.getUserId())){
                alert.setMessage("Шумо аллакай дархости дохилшави рабона кардаед.")
                        .setPositiveButton("Фахмо") {dialog, _ ->
                            dialog.dismiss()
                        }
            }
            else {
                alert.setMessage("Лутфан аз сохибони байтбарак ичозати дохилшави пурсон шавед. Мехохед ичозатнома равон кунем?")
                        .setPositiveButton("Бале") { _, _ ->
                            val notification = Notification(appPreff, NotificationType.BATTLE_JOIN_REQUEST, mBattle.id)
                            viewModel.requestJoin(mBattle, appPreff.getUserId(), notification)
                            Toast.makeText(this, "Ичозатнома равон карда шуд.", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Не") { dialog,_ ->
                            dialog.dismiss()
                        }
            }

            alert.create().show()

        }

        else if (::lastPostedUserId.isInitialized && lastPostedUserId == appPreff.getUserId()){
            Snackbar.make(view, getString(R.string.not_your_turn), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action_invite)) {
                        InviteActivity.newIntent(this, mBattle)
                    }
                    .show()
        }
        else {
            NewCoupletActivity.newIntent(this, mBattle, startingLetter, false)
        }
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.battle, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (!::mBattle.isInitialized){
            return super.onPrepareOptionsMenu(menu)
        }
        else if (mBattle.followers.contains(appPreff.getUserId())) {
            menu?.findItem(R.id.action_follow)?.icon = this.resources.getDrawable(R.drawable.ic_check_circle_white_24dp)
        }
        else {
            menu?.findItem(R.id.action_follow)?.icon = this.resources.getDrawable(R.drawable.ic_add_circle_outline_white_24dp)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_info -> {
                BattleMoreActivity.newIntent(this, mBattle)
                return true
            }
            R.id.action_follow -> {
                if (mBattle.followers.contains(appPreff.getUserId())) {
                    viewModel.unFollowBattle(battleId, appPreff.getUserId())
                    Toast.makeText(this, "Пайрави катъ карда шуд.", Toast.LENGTH_SHORT).show()
                }
                 else {
                    viewModel.followBattle(battleId, appPreff.getUserId())
                    Toast.makeText(this, "Шумо аканун ин Байтбаракро пайрави мекунед", Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}