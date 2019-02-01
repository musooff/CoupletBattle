package com.ballboycorp.battle.battle.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.common.utils.ButtonUtils
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.main.notification.model.NotificationType
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.network.StorageService
import com.ballboycorp.battle.user.UserActivity
import com.ballboycorp.battle.user.model.User
import kotlinx.android.synthetic.main.item_join_request.view.*

/**
 * Created by musooff on 28/01/2019.
 */

class BattleJoinRequestAdapter(private val battleId: String, private val appPref: AppPreference) : RecyclerView.Adapter<BattleJoinRequestAdapter.BattleJoinRequestViewHolder>() {

    private var requestedUsers = ArrayList<User>()

    private val firebaseService = FirebaseService()
    private val storageService = StorageService()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleJoinRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_join_request, parent, false)
        return BattleJoinRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestedUsers.size
    }

    fun submitList(users: List<User>){
        this.requestedUsers = users as ArrayList<User>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BattleJoinRequestViewHolder, position: Int) {
        holder.bind(requestedUsers[position])
    }

    inner class BattleJoinRequestViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(user: User){

            view.user_name.text = user.name
            user.thumbnailUrl?.let {
                GlideApp.with(view.context).load(storageService.getImageRef(it)).into(view.user_thumb)
            }

            ButtonUtils.invalidateButton(view.button_confirm_join, "Тасдик", null, true)
            ButtonUtils.invalidateButton(view.button_reject_join, "Раъд", null, true)

            view.button_confirm_join.setOnClickListener {
                val confirmNotification = Notification(appPref, NotificationType.BATTLE_JOIN_CONFIRMED, battleId)
                firebaseService.confirmJoin(battleId, user.id!!, confirmNotification)
                val position = requestedUsers.indexOf(user)
                requestedUsers.remove(user)
                notifyItemRemoved(position)
            }
            view.button_reject_join.setOnClickListener {
                val rejectNotification = Notification(appPref, NotificationType.BATTLE_JOIN_REJECTED, battleId)
                firebaseService.rejectJoin(battleId, user.id!!, rejectNotification)
                val position = requestedUsers.indexOf(user)
                requestedUsers.remove(user)
                notifyItemRemoved(position)
            }

            view.user_thumb.setOnClickListener {
                UserActivity.newIntent(view.context, user.id!!)
            }

            view.user_name.setOnClickListener {
                UserActivity.newIntent(view.context, user.id!!)
            }
        }
    }
}