package com.ballboycorp.battle.battle.utils

import android.view.View
import android.widget.FrameLayout
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.user.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.battle_header.view.*

/**
 * Created by musooff on 17/01/2019.
 */

object FriendUtil {

    fun updateFriendThumb(friendList: List<User>, view: View){
        view.friends_in_fl.visibility = View.VISIBLE

        val firebaseStorage = FirebaseStorage.getInstance()

        when (friendList.size){
            3 -> {
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[0].thumbnailUrl!!)).into(view.friend_thumb_1)
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[1].thumbnailUrl!!)).into(view.friend_thumb_2)
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[2].thumbnailUrl!!)).into(view.friend_thumb_3)
                view.friend_cv_3.visibility = View.VISIBLE
                view.friend_cv_4.visibility = View.VISIBLE
            }
            2 -> {
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[0].thumbnailUrl!!)).into(view.friend_thumb_1)
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[1].thumbnailUrl!!)).into(view.friend_thumb_2)
                view.friend_thumb_3.setBackgroundResource(R.drawable.friend_thumb_more)
                view.friend_thumb_3.setImageResource(R.drawable.ic_more_horiz_white_24dp)
                view.friend_cv_3.visibility = View.VISIBLE
                view.friend_cv_4.visibility = View.GONE
            }
            1 -> {
                GlideApp.with(view.context).load(firebaseStorage.getReference(friendList[0].thumbnailUrl!!)).into(view.friend_thumb_1)
                view.friend_thumb_2.setBackgroundResource(R.drawable.friend_thumb_more)
                view.friend_thumb_2.setImageResource(R.drawable.ic_more_horiz_white_24dp)
                view.friend_cv_3.visibility = View.GONE
                view.friend_cv_4.visibility = View.GONE
            }
        }
    }
}