package com.ballboycorp.battle.main.home.newbattle.choosefriends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.user.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_choose_friends.view.*

/**
 * Created by musooff on 12/01/2019.
 */

class ChooseFriendsAdapter : RecyclerView.Adapter<ChooseFriendsAdapter.ChooseFriendsViewHolder>(){

    private var chooseFriends: List<User> = ArrayList()
    var chosenFriends = ArrayList<String>()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseFriendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_choose_friends, parent, false)
        return ChooseFriendsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chooseFriends.size
    }

    fun submitList(coupletList: List<User>){
        this.chooseFriends = coupletList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ChooseFriendsViewHolder, position: Int) {
        holder.bind(chooseFriends[position])
    }

    inner class ChooseFriendsViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(user: User){
            view.user_fullname.text = user.name
            GlideApp.with(view.context).load(firebaseStorage.getReference(user.thumbnailUrl!!)).into(view.user_thumb)
            view.user_checkbox.setOnClickListener {
                if (chosenFriends.contains(user.id)){
                    chosenFriends.remove(user.id)
                    view.user_checkbox.isChecked = false
                }
                else {
                    chosenFriends.add(user.id!!)
                    view.user_checkbox.isChecked = true
                }
            }
        }
    }

}
