package com.ballboycorp.battle.main.home.newcoupletcarrier

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
import com.ballboycorp.battle.coupletlist.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.main.home.model.CoupletCarrier
import com.ballboycorp.battle.main.home.model.Privacy
import com.ballboycorp.battle.main.home.newcoupletcarrier.chooseFriends.ChooseFriendsActivity
import kotlinx.android.synthetic.main.activity_newcoupletcarrier.*

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletCarrierActivity : BaseActivity() {

    companion object {

        private const val STARTING_LETTER = "А"

        fun newIntent(context: Context) {
            val intent = Intent(context, NewCoupletCarrierActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(NewCoupletCarrierViewModel::class.java)
    }

    private lateinit var appPreff: AppPreference


    private var privacy = Privacy.PUBLIC
    private lateinit var currentPrivacy: RadioButton

    private var writers: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcoupletcarrier)

        setTitle(getString(R.string.create_battle))
        closeBackButton()

        appPreff = AppPreference.getInstance(this)

        currentPrivacy = privacy_public_rb

        coupletcarrier_submit.setOnClickListener {

            val coupletCarrier = CoupletCarrier()
            coupletCarrier.name = coupletcarrier_name.text.toString()
            coupletCarrier.creatorId = appPreff.getUserId()
            writers.add(appPreff.getUserId())
            coupletCarrier.writers = writers
            coupletCarrier.privacy = getString(privacy.text)

            viewModel.saveCouplet(coupletCarrier)
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
                                selected_friends.text = "${writers.size} friends are added"
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