package com.ballboycorp.battle.coupletlist

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
import com.ballboycorp.battle.coupletlist.newcouplet.NewCoupletActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_coupletlist.*

/**
 * Created by musooff on 12/01/2019.
 */

class CoupletListActivity : BaseActivity() {

    companion object {
        private const val COUPLET_CARRIER_ID = "coupletCarrierId"

        fun newIntent(context: Context, coupletCarrierId: String) {
            val intent = Intent(context, CoupletListActivity::class.java)
            intent.putExtra(COUPLET_CARRIER_ID, coupletCarrierId)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(CoupletListViewModel::class.java)
    }

    private lateinit var adapter: CoupletListAdapter
    private var coupletCarrierId: String? = null
    private var coupletsCount: Int = 0
    private lateinit var lastPostedUserId: String
    private lateinit var startingLetter: String
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupletlist)

        appPreff = AppPreference.getInstance(this)

        coupletCarrierId = intent.extras!!.getString(COUPLET_CARRIER_ID)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        coupletlist_rv.layoutManager = layoutManager

        adapter = CoupletListAdapter()
        coupletlist_rv.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        coupletlist_rv.addItemDecoration(dividerItemDecoration)


        coupletlist_add.setOnClickListener {
            if (lastPostedUserId == appPreff.getUserId()){
                Snackbar.make(coupletlist_add, getString(R.string.not_your_turn), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.action_invite)) {}
                        .show()
            }
            else{
                NewCoupletActivity.newIntent(this, coupletCarrierId!!, coupletsCount, startingLetter, false)
            }
        }

        viewModel.couplets.observe(this, Observer {
            coupletsCount = it.size
            lastPostedUserId = it[coupletsCount - 1].creatorId!!
            startingLetter = it[coupletsCount - 1].endingLetter!!
            adapter.submitList(it)
            coupletlist_rv.smoothScrollToPosition(adapter.itemCount - 1)

        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCouplets(coupletCarrierId!!)
    }
}