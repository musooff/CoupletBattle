package com.ballboycorp.battle.main.home.newcoupletcarrier

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.coupletlist.newcouplet.NewCoupletActivity
import com.ballboycorp.battle.main.home.model.CoupletCarrier
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcoupletcarrier)

        appPreff = AppPreference.getInstance(this)

        coupletcarrier_submit.setOnClickListener {

            val coupletCarrier = CoupletCarrier()
            coupletCarrier.name = coupletcarrier_name.text.toString()
            coupletCarrier.creatorId = appPreff.getUserId()
            coupletCarrier.writers = arrayListOf(appPreff.getUserId())

            viewModel.saveCouplet(coupletCarrier)
                    .addOnSuccessListener {
                        NewCoupletActivity.newIntent(this, it.id, 0, STARTING_LETTER, true)
                        this.finish()
                    }
        }

    }
}