package com.ballboycorp.battle.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.friendlist.FriendListActivity
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.main.notification.model.NotificationType
import com.ballboycorp.battle.user.model.ActionButtonType
import com.ballboycorp.battle.user.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user.*

/**
 * Created by musooff on 13/01/2019.
 */

class UserActivity : BaseActivity() {

    companion object {
        private const val USER_ID = "userId"

        fun newIntent(context: Context, userId: String) {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_ID, userId)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(UserViewModel::class.java)
    }

    private lateinit var userId: String
    private lateinit var mUser: User
    private lateinit var appPreff: AppPreference
    private lateinit var actionButtonType: ActionButtonType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        customAppBar(my_appbar)
        enableBackButton()

        appPreff = AppPreference.getInstance(this)

        userId = intent.extras!!.getString(USER_ID)!!

        viewModel.getUser(userId)
                .get()
                .addOnSuccessListener {
                    if (it != null){
                        val user = it.toObject(User::class.java)
                        mUser = user!!

                        setActionButtonType(user)
                        user_name.text = user.name
                        user_couplet_count.text = user.coupletCount.toString()
                        user_friend_count.text = user.friendCount.toString()

                        GlideApp.with(this).load(viewModel.getImageUrl(user.thumbnailUrl!!)).into(user_thumb)
                        GlideApp.with(this).load(viewModel.getImageUrl(user.coverUrl!!)).into(user_cover)

                    }
                }

        user_friend_count_container.setOnClickListener {
            FriendListActivity.newIntent(this, userId, mUser.friendList.toTypedArray())
        }

    }

    private fun setActionButtonType(user: User) {
        actionButtonType = when {
            appPreff.getUserId() == userId -> ActionButtonType.EDIT
            user.friendList.contains(appPreff.getUserId()) -> ActionButtonType.MESSAGE
            user.friendsPendingFrom.contains(appPreff.getUserId()) -> ActionButtonType.FRIEND_REQUEST_PENDING
            user.friendsPendingTo.contains(appPreff.getUserId()) -> ActionButtonType.FRIEND_REQUEST_SENT
            else -> ActionButtonType.ADD_FRIEND
        }

        action_button.text = getString(actionButtonType.text)

        action_button.setOnClickListener {
            when (actionButtonType){
                ActionButtonType.ADD_FRIEND -> {
                    val notification = Notification()
                    notification.fromUser = appPreff.getUserFullname()
                    notification.fromUserId = appPreff.getUserId()
                    notification.notificationThumbUrl = appPreff.getUserThumbnail()
                    notification.type = NotificationType.FRIEND_REQUEST.value
                    viewModel.sendFriendRequest(userId, notification)
                            .addOnSuccessListener {
                                actionButtonType = ActionButtonType.FRIEND_REQUEST_SENT
                                action_button.text = getString(actionButtonType.text)
                            }
                }
                ActionButtonType.FRIEND_REQUEST_PENDING -> {
                    val notification = Notification()
                    notification.fromUser = appPreff.getUserFullname()
                    notification.fromUserId = appPreff.getUserId()
                    notification.notificationThumbUrl = appPreff.getUserThumbnail()
                    notification.type = NotificationType.FRIEND_ACCEPTED.value
                    viewModel.acceptFriendRequest(userId, notification)
                            .addOnSuccessListener {
                                actionButtonType = ActionButtonType.MESSAGE
                                action_button.text = getString(actionButtonType.text)
                            }
                }
                ActionButtonType.FRIEND_REQUEST_SENT -> {
                    Snackbar.make(it, getString(R.string.already_sent), Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    Snackbar.make(it, getString(R.string.coming_soon), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}