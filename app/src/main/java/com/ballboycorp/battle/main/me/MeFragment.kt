package com.ballboycorp.battle.main.me

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseFragment
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.friendlist.FriendListActivity
import com.ballboycorp.battle.splash.SplashActivity
import com.ballboycorp.battle.user.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_me.view.*

/**
 * Created by musooff on 13/01/2019.
 */

class MeFragment : BaseFragment() {


    private lateinit var userId: String
    private lateinit var mUser: User
    private lateinit var appPreff: AppPreference

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(MeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(" ")

        appPreff = AppPreference.getInstance(view.context)

        userId = appPreff.getUserId()

        viewModel.getUser(userId)
                .get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    mUser = user!!
                    view.user_name.text = user.name
                    view.user_couplet_count.text = "${user.coupletCount}"
                    view.user_friend_count.text = "${user.friendCount}"

                    GlideApp.with(view.context).load(viewModel.getImageUrl(user.thumbnailUrl!!)).into(view.user_thumb)
                    GlideApp.with(view.context).load(viewModel.getImageUrl(user.coverUrl!!)).into(view.user_cover)
                }


        view.button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            appPreff.signOut()
            SplashActivity.newIntent(view.context)
            activity!!.finish()
        }

        view.user_friend_count_container.setOnClickListener {
            FriendListActivity.newIntent(view.context, userId, mUser.friendList.toTypedArray())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.me, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}