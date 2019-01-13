package com.ballboycorp.battle.coupletlist.invite

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
import com.ballboycorp.battle.main.home.newcoupletcarrier.chooseFriends.ChooseFriendsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_invite_friends.*

/**
 * Created by musooff on 14/01/2019.
 */

class InviteActivity  : BaseActivity(){

    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, InviteActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(FriendListViewModel::class.java)
    }

    private lateinit var adapter: ChooseFriendsAdapter

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)


        appPreff = AppPreference.getInstance(this)

        val layoutManager = LinearLayoutManager(this)
        friendlist_rv.layoutManager = layoutManager

        adapter = ChooseFriendsAdapter()
        friendlist_rv.adapter = adapter

        viewModel.friedList.observe(this, Observer {
            adapter.submitList(it)
        })

        button_invite_friends.setOnClickListener {
            Snackbar.make(it, getString(R.string.coming_soon), Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriendIds(appPreff.getUserId())
    }
}