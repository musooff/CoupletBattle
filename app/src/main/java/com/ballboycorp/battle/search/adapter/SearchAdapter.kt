package com.ballboycorp.battle.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.search.model.SearchType
import com.ballboycorp.battle.user.model.User
import kotlinx.android.synthetic.main.search_result_all.view.*
import kotlinx.android.synthetic.main.search_result_single.view.*

/**
 * Created by musooff on 19/01/2019.
 */

class SearchAdapter(private val pagerListener: ViewPagerListener) : PagerAdapter(){

    companion object {
        private const val ALL = "Хама"
        private const val BATTLES = "Байтбарак"
        private const val USERS = "Шахс"
        private const val AUTHORS = "Шоир"

        private const val EMPTY_ALL = "Maълумоте бо чунин ном ёфт нашуд."
        private const val EMPTY_BATTLES = "Байтбараке бо чунин ном ёфт нашуд."
        private const val EMPTY_USERS = "Шахсе бо чунин ном ёфт нашуд."
        private const val EMPTY_AUTHORS = "Шоире бо чунин ном ёфт нашуд."
    }

    private val pageReferences = HashMap<String, View>()

    private val allBattlesSearchResultAdapter = SearchResultAdapter(SearchType.BATTLE)
    private val allUsersSearchResultAdapter = SearchResultAdapter(SearchType.USER)
    private val allAuthorsSearchResultAdapter = SearchResultAdapter(SearchType.AUTHOR)

    private val battlesSearchResultAdapter = SearchResultAdapter(SearchType.BATTLE)
    private val usersSearchResultAdapter = SearchResultAdapter(SearchType.USER)
    private val authorsSearchResultAdapter = SearchResultAdapter(SearchType.AUTHOR)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as RelativeLayout

    }

    override fun getCount(): Int {
        return 4
    }

    fun submitList(result: List<Any>){
        allBattlesSearchResultAdapter.submitList(result[0] as List<Battle>)
        allUsersSearchResultAdapter.submitList(result[1] as List<User>)
        allAuthorsSearchResultAdapter.submitList(result[2] as List<Author>)

        battlesSearchResultAdapter.submitList(result[0] as List<Battle>)
        usersSearchResultAdapter.submitList(result[1] as List<User>)
        authorsSearchResultAdapter.submitList(result[2] as List<Author>)
        updateEmptyView(result as List<List<Any>>)
    }

    private fun updateEmptyView(result: List<List<Any>>){
        var totalCount = 0
        result.forEach { totalCount += it.size }
        if (totalCount == 0){
            pageReferences[ALL]?.empty_all?.visibility = View.VISIBLE
            pageReferences[ALL]?.empty_all?.findViewById<TextView>(R.id.empty_text)?.text = EMPTY_ALL
            pageReferences[ALL]?.container?.visibility = View.INVISIBLE
        }
        else {
            pageReferences[ALL]?.empty_all?.visibility = View.GONE
            pageReferences[ALL]?.container?.visibility = View.VISIBLE
        }
        if (result[0].isEmpty()){
            pageReferences[BATTLES]?.empty?.visibility = View.VISIBLE
            pageReferences[BATTLES]?.empty?.findViewById<TextView>(R.id.empty_text)?.text = EMPTY_BATTLES
        }
        else {
            pageReferences[BATTLES]?.empty?.visibility = View.GONE
        }
        if (result[1].isEmpty()){
            pageReferences[USERS]?.empty?.visibility = View.VISIBLE
            pageReferences[USERS]?.empty?.findViewById<TextView>(R.id.empty_text)?.text = EMPTY_USERS
        }
        else {
            pageReferences[USERS]?.empty?.visibility = View.GONE
        }
        if (result[2].isEmpty()){
            pageReferences[AUTHORS]?.empty?.visibility = View.VISIBLE
            pageReferences[AUTHORS]?.empty?.findViewById<TextView>(R.id.empty_text)?.text = EMPTY_AUTHORS

        }
        else {
            pageReferences[AUTHORS]?.empty?.visibility = View.GONE

        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(container.context).inflate(R.layout.search_result_single, container, false)
        when (position) {
            0 -> {
                view = LayoutInflater.from(container.context).inflate(R.layout.search_result_all, container, false)
                view.battle_rv.adapter = allBattlesSearchResultAdapter
                view.battle_rv.layoutManager = LinearLayoutManager(container.context)
                view.battles_tv.setOnClickListener { pagerListener.setViewPager(1) }
                view.users_rv.adapter = allUsersSearchResultAdapter
                view.users_rv.layoutManager = LinearLayoutManager(container.context)
                view.users_tv.setOnClickListener { pagerListener.setViewPager(2) }
                view.authors_rv.adapter = allAuthorsSearchResultAdapter
                view.authors_rv.layoutManager = LinearLayoutManager(container.context)
                view.authors_tv.setOnClickListener { pagerListener.setViewPager(3) }
                pageReferences[ALL] = view
            }
            1 -> {
                view.recycler_view.adapter = battlesSearchResultAdapter
                view.recycler_view.layoutManager = LinearLayoutManager(container.context)
                pageReferences[BATTLES] = view
            }
            2 -> {
                view.recycler_view.adapter = usersSearchResultAdapter
                view.recycler_view.layoutManager = LinearLayoutManager(container.context)
                pageReferences[USERS] = view
            }
            3 -> {
                view.recycler_view.adapter = authorsSearchResultAdapter
                view.recycler_view.layoutManager = LinearLayoutManager(container.context)
                pageReferences[AUTHORS] = view
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
        when (position) {
            0 -> pageReferences.remove(ALL)
            1 -> pageReferences.remove(BATTLES)
            2 -> pageReferences.remove(USERS)
            3 -> pageReferences.remove(AUTHORS)
        }
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