package com.ballboycorp.battle.main.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.user.UserActivity
import com.ballboycorp.battle.user.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_top_user.view.*

/**
 * Created by musooff on 16/01/2019.
 */

class HomeTopUsersAdapter : RecyclerView.Adapter<HomeTopUsersAdapter.HomeTopUsersViewHolder>() {

    private var topUsers: List<User> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopUsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_user, parent, false)
        return HomeTopUsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topUsers.size
    }

    fun submitList(topUsers: List<User>){
        this.topUsers = topUsers.sortedByDescending {
            it.coupletCount
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeTopUsersViewHolder, position: Int) {
        holder.bind(topUsers[position], position)
    }

    inner class HomeTopUsersViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(user: User, position: Int){


            view.rank.text = if (position == 0) "" else (position + 1).toString()
            view.rank.background = if (position == 0) ContextCompat.getDrawable(itemView.context, R.drawable.ic_top_rank_s) else null

            view.user_name.text = user.name
            view.user_couplet_count.text = String.format(view.context.getString(R.string.couplet_count_format), user.coupletCount)

            GlideApp.with(view.context).load(firebaseStorage.getReference(user.thumbnailUrl!!)).into(view.image)

            view.setOnClickListener {
                UserActivity.newIntent(view.context, user.id!!)
            }
        }
    }
}