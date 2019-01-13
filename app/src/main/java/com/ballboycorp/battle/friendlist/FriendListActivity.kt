package com.ballboycorp.battle.friendlist

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
import com.ballboycorp.battle.coupletlist.CoupletListActivity
import kotlinx.android.synthetic.main.activity_friendlist.*

/**
 * Created by musooff on 12/01/2019.
 */

class FriendListActivity : BaseActivity() {

    companion object {
        private const val FRIEND_IDS = "friendIds"

        fun newIntent(context: Context, friendIds: Array<String>) {
            val intent = Intent(context, FriendListActivity::class.java)
            intent.putExtra(FRIEND_IDS, friendIds)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(FriendListViewModel::class.java)
    }

    private lateinit var adapter: FriendListAdapter
    private var friendIds: Array<String> = arrayOf()
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friendlist)

        appPreff = AppPreference.getInstance(this)

        friendIds = intent.extras!!.getStringArray(FRIEND_IDS)!!

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        friendlist_rv.layoutManager = layoutManager

        adapter = FriendListAdapter()
        friendlist_rv.adapter = adapter


        viewModel.friedList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriendList(friendIds.toList())
    }
}