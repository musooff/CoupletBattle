package com.ballboycorp.battle.battle.invite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.friendlist.FriendListViewModel
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.home.newbattle.choosefriends.ChooseFriendsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_invite_friends.*

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


        appPreff = AppPreference.getInstance(this)

        mBattle = intent!!.extras!!.getSerializable(BATTLE) as Battle

        val layoutManager = LinearLayoutManager(this)
        friendlist_rv.layoutManager = layoutManager

        adapter = ChooseFriendsAdapter()
        friendlist_rv.adapter = adapter

        viewModel.friedList.observe(this, Observer {
            adapter.submitList(
                    it.filter {
                        !mBattle.writers.contains(it.id)
                    }
            )
        })

        button_invite_friends.setOnClickListener {
            adapter.chosenFriends
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriendIds(appPreff.getUserId())
    }
}