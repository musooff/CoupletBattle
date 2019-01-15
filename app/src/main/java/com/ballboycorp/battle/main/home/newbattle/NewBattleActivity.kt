package com.ballboycorp.battle.main.home.newbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.home.model.Privacy
import com.ballboycorp.battle.main.home.newbattle.choosefriends.ChooseFriendsActivity
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.main.notification.model.NotificationType
import kotlinx.android.synthetic.main.activity_new_battle.*

/**
 * Created by musooff on 12/01/2019.
 */

class NewBattleActivity : BaseActivity() {

    companion object {

        private const val STARTING_LETTER = "А"

        fun newIntent(context: Context) {
            val intent = Intent(context, NewBattleActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(NewBattleViewModel::class.java)
    }

    private lateinit var appPreff: AppPreference


    private var privacy = Privacy.PUBLIC
    private lateinit var currentPrivacy: RadioButton

    private var writers: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_battle)

        setTitle(getString(R.string.title_create_battle))
        enableBackButton(true)

        appPreff = AppPreference.getInstance(this)

        currentPrivacy = privacy_public_rb

        continue_bt.setOnClickListener {

            val battle = Battle()
            battle.name = battle_name.text.toString()
            battle.creatorId = appPreff.getUserId()
            writers.add(appPreff.getUserId())
            battle.writers = writers
            battle.privacy = getString(privacy.text)


            val notification = Notification()
            notification.fromUser = appPreff.getUserFullname()
            notification.fromUserId = appPreff.getUserId()
            notification.notificationThumbUrl = appPreff.getUserThumbnail()
            notification.type = NotificationType.BATTLE_JOINED.value

            viewModel.saveCouplet(battle, notification)
                    .addOnSuccessListener {
                        NewCoupletActivity.newIntent(this, it.id, 0, STARTING_LETTER, true)
                        this.finish()
                    }
        }

    }

    fun onPrivacyChanged(view: View){
        currentPrivacy.isChecked = false
        when (view.id){
            R.id.privacy_public_rb -> {
                currentPrivacy = privacy_public_rb
                privacy = Privacy.PUBLIC
            }
            R.id.privacy_private_rb -> {
                currentPrivacy = privacy_private_rb
                privacy = Privacy.PRIVATE
            }
            R.id.privacy_secret_rb -> {
                currentPrivacy = privacy_secret_rb
                privacy = Privacy.SECRET
            }
        }
        currentPrivacy.isChecked = true
    }

    fun chooseFriends(view: View){
        ChooseFriendsActivity.newIntent(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ChooseFriendsActivity.ACTIVITY_RESULT){
            if (resultCode == Activity.RESULT_OK){
                data?.extras?.getStringArrayList(ChooseFriendsActivity.SELECTED_LIST)
                        ?.let {
                            writers = it
                            if (writers.isNotEmpty()){
                                selected_friends.text = String.format(getString(R.string.number_of_friends_added), it.size)
                            }
                            else{
                                selected_friends.text = getString(R.string.choose_friends_desc)
                            }
                        }

            }
            else{
                writers = ArrayList()
                selected_friends.text = getString(R.string.choose_friends_desc)
            }
        }
    }
}