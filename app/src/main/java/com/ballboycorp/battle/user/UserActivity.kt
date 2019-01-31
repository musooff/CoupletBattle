package com.ballboycorp.battle.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.common.utils.ButtonUtils
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

    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        customAppBar(my_appbar)
        enableBackButton()

        appPreff = AppPreference.getInstance(this)

        userId = intent.extras!!.getString(USER_ID)!!

        viewModel.getUser(userId)
                .addOnSuccessListener {
                    if (it != null){
                        val user = it.toObject(User::class.java)
                        mUser = user!!

                        setLastPosts(mUser)

                        setActionButtonType(user)
                        user_name.text = user.name
                        user_couplet_count.text = user.coupletCount.toString()
                        user_friend_count.text = user.friendCount.toString()

                        GlideApp.with(this).load(viewModel.getImageUrl(user.thumbnailUrl!!)).into(user_thumb)
                        GlideApp.with(this).load(viewModel.getImageUrl(user.coverUrl!!)).into(user_cover)

                        viewModel.getUserPosts(userId)

                    }
                }

        user_friend_count_container.setOnClickListener {
            FriendListActivity.newIntent(this, userId, mUser.friendList.toTypedArray())
        }


        viewModel.userPosts.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun setLastPosts(user: User){
        val layoutManager = LinearLayoutManager(this)
        posts_rv.layoutManager = layoutManager
        adapter = PostAdapter(user)
        posts_rv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        posts_rv.addItemDecoration(dividerItemDecoration)
    }

    private fun setActionButtonType(user: User) {
        if (appPreff.getUserId() == userId){
            return
        }
        actionButtonType = when {
            user.friendList.contains(appPreff.getUserId()) -> ActionButtonType.MESSAGE
            user.friendsPendingFrom.contains(appPreff.getUserId()) -> ActionButtonType.FRIEND_REQUEST_PENDING
            user.friendsPendingTo.contains(appPreff.getUserId()) -> ActionButtonType.FRIEND_REQUEST_SENT
            else -> ActionButtonType.ADD_FRIEND
        }

        button_action_button.visibility = View.VISIBLE

        ButtonUtils.invalidateButton(button_action_button, getString(actionButtonType.text), actionButtonType.drawableLeft, true)


        button_action_button.setOnClickListener {
            when (actionButtonType){
                ActionButtonType.ADD_FRIEND -> {
                    val notification = Notification(appPreff, NotificationType.FRIEND_REQUEST)
                    viewModel.sendFriendRequest(userId, notification)
                            .addOnSuccessListener {
                                actionButtonType = ActionButtonType.FRIEND_REQUEST_SENT
                                ButtonUtils.invalidateButton(button_action_button, getString(actionButtonType.text), actionButtonType.drawableLeft, true)
                            }
                }
                ActionButtonType.FRIEND_REQUEST_PENDING -> {
                    val notification = Notification(appPreff, NotificationType.FRIEND_ACCEPTED)
                    viewModel.acceptFriendRequest(userId, notification)
                            .addOnSuccessListener {
                                actionButtonType = ActionButtonType.MESSAGE
                                ButtonUtils.invalidateButton(button_action_button, getString(actionButtonType.text), actionButtonType.drawableLeft, true)
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