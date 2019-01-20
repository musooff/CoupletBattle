package com.ballboycorp.battle.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.search.adapter.SearchAdapter
import com.ballboycorp.battle.search.adapter.ViewPagerListener
import com.ballboycorp.battle.user.model.User
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by musooff on 17/01/2019.
 */

class SearchActivity : BaseActivity(), ViewPagerListener {

    companion object {
        fun newIntent(context: Context){
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var adapter: SearchAdapter

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        customAppBar(my_appbar)

        viewModel.result.observe(this, Observer {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    val resultToShow = ArrayList<Any>()
                    if (newText == ""){
                        content.visibility = View.INVISIBLE
                        setViewPager(0)
                        resultToShow.add(arrayListOf<Battle>())
                        resultToShow.add(arrayListOf<User>())
                        resultToShow.add(arrayListOf<Author>())
                        adapter.submitList(resultToShow)
                        return false
                    }
                    content.visibility = View.VISIBLE
                    resultToShow.add((it[0] as List<Battle>).filter {
                        it.name!!.toLowerCase().contains(newText.toLowerCase())
                    })
                    resultToShow.add((it[1] as List<User>).filter {
                        it.name!!.toLowerCase().contains(newText.toLowerCase())
                    })
                    resultToShow.add((it[2] as List<Author>).filter {
                        it.name!!.toLowerCase().contains(newText.toLowerCase())
                    })
                    adapter.submitList(resultToShow)
                    return false
                }
            })
        })

        adapter = SearchAdapter(this)
        search_tab_indicator.setupWithViewPager(search_result_pager)
        search_result_pager.adapter = adapter
        search_result_pager.offscreenPageLimit = 3

        viewModel.loadDatabase()
    }

    override fun setViewPager(position: Int) {
        search_result_pager.currentItem = position
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_cancel -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}