package com.ballboycorp.battle.main.me

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseFragment
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.common.utils.ImageUtils
import com.ballboycorp.battle.common.utils.PermissionUtils
import com.ballboycorp.battle.friendlist.FriendListActivity
import com.ballboycorp.battle.splash.SplashActivity
import com.ballboycorp.battle.user.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_me.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.data
import com.ballboycorp.battle.common.utils.ButtonUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_me.*
import java.io.FileNotFoundException


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


        loadUserData()

        ButtonUtils.invalidateButton(button_logout, getString(R.string.button_logout), R.drawable.ic_exit_white_24dp, true)
        view.button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            appPreff.signOut()
            SplashActivity.newIntent(view.context)
            activity!!.finish()
        }

        view.user_friend_count_container.setOnClickListener {
            FriendListActivity.newIntent(view.context, userId, mUser.friendList.toTypedArray())
        }

        view.edit_cover.setOnClickListener {
            PermissionUtils.requestStorage(activity!!, object : PermissionUtils.OnPermissionResult {
                override fun onResult(requestCode: Int, granted: Boolean, permissions: Array<String>) {
                    if (granted) {
                        ImageUtils.newCameraIntentCover(this@MeFragment)
                    }
                }

            })
        }

        view.edit_thumb.setOnClickListener {
            PermissionUtils.requestStorage(activity!!, object : PermissionUtils.OnPermissionResult {
                override fun onResult(requestCode: Int, granted: Boolean, permissions: Array<String>) {
                    if (granted) {
                        ImageUtils.newCameraIntentThumbnail(this@MeFragment)
                    }
                }

            })
        }
    }

    private fun loadUserData(){
        viewModel.getUser(userId)
                .get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    mUser = user!!
                    view?.let {
                        it.user_name.text = user.name
                        it.user_couplet_count.text = "${user.coupletCount}"
                        it.user_friend_count.text = "${user.friendCount}"


                        GlideApp.with(it.context)
                                .load(viewModel.getImageUrl(user.thumbnailUrl!!))
                                .into(it.user_thumb)
                        GlideApp.with(it.context)
                                .load(viewModel.getImageUrl(user.coverUrl!!))
                                .into(it.user_cover)

                        it.about_name.text = user.name
                        it.about_email.text = user.email ?: getString(R.string.does_not_exists)
                        it.about_phone.text = user.phoneNumber ?: getString(R.string.does_not_exists)

                    }

                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageUtils.ACTIVITY_RESULT_COVER){
            if (resultCode == Activity.RESULT_OK){
                data?.data?.let {
                    val metadata = StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .build()
                    val storageReference = FirebaseStorage.getInstance().reference
                    val uploadTask = storageReference.child("users/covers/${userId}_${System.currentTimeMillis()}.jpg").putFile(it, metadata)
                    uploadTask
                            .addOnProgressListener {taskSnapshot ->
                                val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                                System.out.println("Upload is $progress% done")
                            }
                            .addOnSuccessListener {
                                viewModel.updateCoverUrl(userId, it.metadata?.path)
                                        .addOnSuccessListener {
                                            loadUserData()
                                        }
                            }
                }

            }
        }
        else if (requestCode == ImageUtils.ACTIVITY_RESULT_THUMBNAIL){
            if (resultCode == Activity.RESULT_OK){
                data?.data?.let {
                    val metadata = StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .build()
                    val storageReference = FirebaseStorage.getInstance().reference
                    val uploadTask = storageReference.child("users/thumbnails/${userId}_${System.currentTimeMillis()}.jpg").putFile(it, metadata)
                    uploadTask
                            .addOnProgressListener {taskSnapshot ->
                                val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                                view!!.progress_circular.setProgressWithAnimation(progress.toFloat(), 2500)
                            }
                            .addOnSuccessListener { storage ->
                                viewModel.updateThumbnailUrl(userId, storage.metadata?.path)
                                        .addOnSuccessListener {
                                            view!!.progress_circular.progress = 0f
                                            appPreff.setUserThumbnail(storage.metadata?.path!!)
                                            loadUserData()
                                        }
                            }
                }

            }
        }
    }
}