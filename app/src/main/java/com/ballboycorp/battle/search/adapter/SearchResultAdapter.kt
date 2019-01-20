package com.ballboycorp.battle.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.AuthorActivity
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.search.model.SearchType
import com.ballboycorp.battle.user.UserActivity
import com.ballboycorp.battle.user.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_search_result.view.*

/**
 * Created by musooff on 19/01/2019.
 */

class SearchResultAdapter(private val searchType: SearchType): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<Any> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (searchType){
            SearchType.ALL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
                BattlesSearchResultViwHolder(view)
            }
            SearchType.BATTLE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
                BattlesSearchResultViwHolder(view)
            }
            SearchType.USER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
                UsersSearchResultViwHolder(view)
            }
            SearchType.AUTHOR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
                AuthorsSearchResultViwHolder(view)
            }
        }
    }

    fun submitList(items: List<Any>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (searchType){
            SearchType.ALL -> (holder as BattlesSearchResultViwHolder).bind(items[position] as Battle)
            SearchType.BATTLE -> (holder as BattlesSearchResultViwHolder).bind(items[position] as Battle)
            SearchType.USER -> (holder as UsersSearchResultViwHolder).bind(items[position] as User)
            SearchType.AUTHOR -> (holder as AuthorsSearchResultViwHolder).bind(items[position] as Author)
        }
    }

    inner class BattlesSearchResultViwHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(battle: Battle){
            view.search_title.text = battle.name
            view.search_sub_title.text = String.format(view.context.getString(R.string.couplet_count_format), battle.coupletCount)
            battle.thumbnailUrl?.let {
                GlideApp.with(view).load(firebaseStorage.getReference(it)).into(view.image)
            }

            view.setOnClickListener {
                BattleActivity.newIntent(view.context, battle.id!!)
            }
        }
    }

    inner class UsersSearchResultViwHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(user: User){
            view.search_title.text = user.name
            view.search_sub_title.text = String.format(view.context.getString(R.string.couplet_count_format), user.coupletCount)
            user.thumbnailUrl?.let {
                GlideApp.with(view).load(firebaseStorage.getReference(it)).into(view.image)
            }

            view.setOnClickListener {
                UserActivity.newIntent(view.context, user.id!!)
            }
        }
    }

    inner class AuthorsSearchResultViwHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(author: Author){
            view.search_title.text = author.name
            view.search_sub_title.text = String.format(view.context.getString(R.string.couplet_count_format), author.coupletCount)
            author.thumbnailUrl?.let {
                GlideApp.with(view).load(firebaseStorage.getReference(it)).into(view.image)
            }

            view.setOnClickListener {
                AuthorActivity.newIntent(view.context, author.id!!)
            }
        }
    }
}