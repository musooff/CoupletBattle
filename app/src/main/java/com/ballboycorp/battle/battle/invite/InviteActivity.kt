package com.ballboycorp.battle.battle.invite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.friendlist.FriendListViewModel
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.home.newbattle.choosefriends.ChooseFriendsAdapter
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.main.notification.model.NotificationType
import kotlinx.android.synthetic.main.activity_invite_friends.*
import kotlinx.android.synthetic.main.empty_list.*

/**
 * Created by musooff on 14/01/2019.
 */

class InviteActivity  : BaseActivity(){

    companion object {

        private const val BATTLE = "battle"
        fun newIntent(context: Context, battle: Battle) {
            val intent = Intent(context, InviteActivity::class.java)
            intent.putExtra(BATTLE, battle)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(FriendListViewModel::class.java)
    }

    private lateinit var adapter: ChooseFriendsAdapter

    private lateinit var mBattle: Battle

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)

        enableBackButton(true)
        setTitle(getString(R.string.title_invite_friends))

        appPreff = AppPreference.getInstance(this)

        mBattle = intent!!.extras!!.getSerializable(BATTLE) as Battle

        val layoutManager = LinearLayoutManager(this)
        friendlist_rv.layoutManager = layoutManager

        adapter = ChooseFriendsAdapter()
        friendlist_rv.adapter = adapter

        viewModel.friedList.observe(this, Observer {
            adapter.submitList(
                    it.filter {
                        !mBattle.followers.contains(it.id)
                    }
            )
            invalidateEmptyList(adapter.isEmpty())
        })

        button_invite_friends.setOnClickListener {

            val notification = Notification()
            notification.fromUser = appPreff.getUserName()
            notification.fromUserId = appPreff.getUserId()
            notification.notificationThumbUrl = appPreff.getUserThumbnail()
            notification.type = NotificationType.BATTLE_INVITATION.value
            notification.battleId = mBattle.id

            viewModel.sendInvitations(adapter.chosenFriends, notification)
            Toast.makeText(this, "Invitations to join the battle has been sent.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriendIds(appPreff.getUserId())
    }

    private fun invalidateEmptyList(isEmpty: Boolean){
        if (isEmpty){
            empty.visibility = View.VISIBLE
            empty_text.text = getString(R.string.empty_invite)
        }
        else{
            empty.visibility = View.GONE

        }
    }
}