package com.ballboycorp.battle.battle.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.R
import com.ballboycorp.battle.user.model.User

/**
 * Created by musooff on 28/01/2019.
 */

class BattleJoinRequestAdapter : RecyclerView.Adapter<BattleJoinRequestAdapter.BattleJoinRequestViewHolder>() {
    private var requestedUsers: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleJoinRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_join_request, parent, false)
        return BattleJoinRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestedUsers.size
    }

    fun submitList(users: List<User>){
        this.requestedUsers = users
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BattleJoinRequestViewHolder, position: Int) {
        holder.bind(requestedUsers[position])
    }

    inner class BattleJoinRequestViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(user: User){

        }
    }
}