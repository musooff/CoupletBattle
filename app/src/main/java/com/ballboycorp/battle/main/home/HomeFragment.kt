package com.ballboycorp.battle.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseFragment
import com.ballboycorp.battle.main.home.newcoupletcarrier.NewCoupletCarrierActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by musooff on 12/01/2019.
 */

class HomeFragment : BaseFragment() {


    private lateinit var adapter: HomeAdapter
    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        coupletcarrier_rv.layoutManager = layoutManager

        adapter = HomeAdapter()
        coupletcarrier_rv.adapter = adapter

        viewModel.coupletCarriers.observe(this, Observer {
            adapter.submitList(it)
        })

        coupletcarrier_add.setOnClickListener {
            NewCoupletCarrierActivity.newIntent(activity!!)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCoupletCarriers()
    }
}