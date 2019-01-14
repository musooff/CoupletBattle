package com.ballboycorp.battle.friendlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.user.UserActivity
import com.ballboycorp.battle.user.model.User
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_friendlist.view.*

/**
 * Created by musooff on 12/01/2019.
 */

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>(){

    private var friendList: List<User> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friendlist, parent, false)
        return FriendListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    fun submitList(coupletList: List<User>){
        this.friendList = coupletList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(friendList[position])
    }

    inner class FriendListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(friend: User){
            view.user_fullname.text = friend.name
            GlideApp.with(view.context).load(firebaseStorage.getReference(friend.thumbnailUrl!!)).into(view.user_thumb)
            view.setOnClickListener {
                UserActivity.newIntent(view.context, friend.id!!)
            }
        }
    }

}
