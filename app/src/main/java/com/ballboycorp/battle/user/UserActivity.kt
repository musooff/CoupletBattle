package com.ballboycorp.battle.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.user.model.User
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
    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        appPreff = AppPreference.getInstance(this)

        userId = intent.extras!!.getString(USER_ID)!!

        viewModel.getUser(userId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null){
                        Log.e("ERROR", exception.message)
                    }
                    else if (snapshot != null){
                        val user = snapshot.toObject(User::class.java)
                        user_name.text = user!!.name
                        user_couplet_count.text = "${user.coupletCount}"
                        user_friend_count.text = "${user.friendCount}"

                        invalidateActionButton(user)

                    }
                }


    }

    private fun invalidateActionButton(user: User) {
        when {
            appPreff.getUserId() == userId -> action_button.text = getString(R.string.edit_button)
            user.friendList.contains(appPreff.getUserId()) -> action_button.text = getString(R.string.message_button)
            else -> action_button.text = getString(R.string.button_addfriend)
        }
    }
}