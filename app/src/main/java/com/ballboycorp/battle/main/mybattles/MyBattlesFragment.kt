package com.ballboycorp.battle.main.mybattles

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseFragment
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.main.home.adapters.HomeAdapter
import com.ballboycorp.battle.main.home.newbattle.NewBattleActivity
import com.ballboycorp.battle.search.SearchActivity
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * Created by musooff on 14/01/2019.
 */

class MyBattlesFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(MyBattlesViewModel::class.java)
    }
    private lateinit var adapter: HomeAdapter
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.title_my))


        appPreff = AppPreference.getInstance(view.context)

        val layoutManager = LinearLayoutManager(activity)
        battle_rv.layoutManager = layoutManager

        adapter = HomeAdapter()
        battle_rv.adapter = adapter

        viewModel.battles.observe(this, Observer {
            adapter.submitList(it)
            invalidateEmptyList(adapter.isEmpty())
        })

        battle_add.setOnClickListener {
            NewBattleActivity.newIntent(activity!!)
        }

        viewModel.getMyBattles(appPreff.getUserId())


    }

    private fun invalidateEmptyList(isEmpty: Boolean){
        if (isEmpty){
            empty.visibility = View.VISIBLE
            empty_text.text = getString(R.string.empty_my_battles)
        }
        else{
            empty.visibility = View.GONE

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.my, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_search -> SearchActivity.newIntent(context!!)
        }
        return super.onOptionsItemSelected(item)
    }
}