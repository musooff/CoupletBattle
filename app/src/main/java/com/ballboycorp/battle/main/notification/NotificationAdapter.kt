package com.ballboycorp.battle.main.notification

import android.graphics.Typeface
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.main.notification.model.Notification
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_notification.view.*
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.main.notification.model.NotificationType
import com.ballboycorp.battle.user.UserActivity


/**
 * Created by musooff on 12/01/2019.
 */

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){

    private var notifications: List<Notification> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun isEmpty(): Boolean {
        return notifications.isEmpty()
    }

    fun submitList(coupletList: List<Notification>){
        this.notifications = coupletList.sortedByDescending {
            it.createdTime
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    inner class NotificationViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(notification: Notification){

            GlideApp.with(view.context).load(firebaseStorage.getReference(notification.notificationThumbUrl!!)).into(view.notification_thumb)

            val text = SpannableStringBuilder()
            when (notification.type){
                NotificationType.FRIEND_REQUEST.value -> {
                    text.append("${notification.fromUser} sent you friend request.")
                }
                NotificationType.FRIEND_ACCEPTED.value -> {
                    text.append("${notification.fromUser} accepted your friend request.")

                }
                NotificationType.BATTLE_JOINED.value -> {
                    text.append("${notification.fromUser} added you to a Байтбарак")

                }
                NotificationType.BATTLE_INVITATION.value -> {
                    text.append("${notification.fromUser} invited you to join a Байтбарак")

                }
            }
            text.setSpan(StyleSpan(Typeface.BOLD), 0, notification.fromUser!!.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            view.notification_text.text = text

            view.setOnClickListener {
                if (NotificationType.isBattleType(notification.type!!)) {
                    BattleActivity.newIntent(view.context, notification.battleId!!)
                }
                else {
                    UserActivity.newIntent(view.context, notification.fromUserId!!)
                }
            }
        }
    }

}
