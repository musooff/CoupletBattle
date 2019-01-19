package com.ballboycorp.battle.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.search.model.SearchType
import com.ballboycorp.battle.user.model.User
import kotlinx.android.synthetic.main.search_result_single.view.*

/**
 * Created by musooff on 19/01/2019.
 */

class SearchAdapter : PagerAdapter(){

    companion object {
        private const val ALL = "Хама"
        private const val BATTLES = "Байтбарак"
        private const val USERS = "Шахс"
        private const val AUTHORS = "Шоир"
    }

    private val allSearchResultAdapter = SearchResultAdapter(SearchType.ALL)
    private val battlesSearchResultAdapter = SearchResultAdapter(SearchType.BATTLE)
    private val usersSearchResultAdapter = SearchResultAdapter(SearchType.USER)
    private val authorsSearchResultAdapter = SearchResultAdapter(SearchType.AUTHOR)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as RelativeLayout

    }

    override fun getCount(): Int {
        return 4
    }

    fun submitList(result: List<Any>, empty: View){

        //allSearchResultAdapter.submitList(result)
        battlesSearchResultAdapter.submitList(result[0] as List<Battle>, empty)
        usersSearchResultAdapter.submitList(result[1] as List<User>, empty)
        authorsSearchResultAdapter.submitList(result[2] as List<Author>, empty)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.search_result_single, container, false)
        view.recycler_view.layoutManager = LinearLayoutManager(container.context)
        when (position) {
            0 -> view.recycler_view.adapter = allSearchResultAdapter
            1 -> view.recycler_view.adapter = battlesSearchResultAdapter
            2 -> view.recycler_view.adapter = usersSearchResultAdapter
            3 -> view.recycler_view.adapter = authorsSearchResultAdapter
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> ALL
            1 -> BATTLES
            2 -> USERS
            3 -> AUTHORS
            else -> ""
        }
    }
}