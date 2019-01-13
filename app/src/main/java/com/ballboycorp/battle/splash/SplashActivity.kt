package com.ballboycorp.battle.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.main.MainActivity
import com.ballboycorp.battle.user.model.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by musooff on 12/01/2019.
 */

class SplashActivity : BaseActivity() {

    companion object {
        private const val RC_SIGN_IN = 13

        fun newIntent(context: Context){
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SplashViewModel::class.java)
    }

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        appPreff = AppPreference.getInstance(this)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            MainActivity.newIntent(this)
            this.finish()
        } else {

            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder()
                        .build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .build(),
                RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                val user = User.toUser(firebaseUser!!)
                viewModel.updateUserCredentials(user, appPreff)
                MainActivity.newIntent(this)
                finish()
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled)
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection)
                    return
                }

                showSnackbar(R.string.unknown_error)
                Log.e("ERROR", "Sign-in error: ", response.error)
            }
        }
    }

    private fun showSnackbar(textId: Int) {
        Snackbar.make(splash_main, textId, Snackbar.LENGTH_SHORT)
                .show();    }
}