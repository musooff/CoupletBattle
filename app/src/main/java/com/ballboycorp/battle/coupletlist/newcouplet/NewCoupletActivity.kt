package com.ballboycorp.battle.coupletlist.newcouplet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.coupletlist.CoupletListActivity
import com.ballboycorp.battle.coupletlist.model.Couplet
import kotlinx.android.synthetic.main.activity_newcouplet.*
import java.util.*

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletActivity : BaseActivity() {

    companion object {

        private const val COUPLETS_COUNT = "coupletsCount"
        private const val COUPLET_CARRIER_ID = "coupletCarrierId"
        private const val STARTING_LETTER = "startingLetter"
        private const val OPEN_COUPLETLIST = "openCoupletList"

        fun newIntent(context: Context, coupletCarrierId: String, coupletsCount: Int, startingLetter: String, openCoupletList: Boolean) {
            val intent = Intent(context, NewCoupletActivity::class.java)
            intent.putExtra(COUPLETS_COUNT, coupletsCount)
            intent.putExtra(COUPLET_CARRIER_ID, coupletCarrierId)
            intent.putExtra(STARTING_LETTER, startingLetter)
            intent.putExtra(OPEN_COUPLETLIST, openCoupletList)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(NewCoupletViewModel::class.java)
    }

    private var coupletsCount: Int = 0
    private var openCoupletList: Boolean = false
    private var coupletCarrierId: String? = null
    private var startingLetter: String? = null

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcouplet)

        appPreff = AppPreference.getInstance(this)

        coupletsCount = intent.extras!!.getInt(COUPLETS_COUNT)
        coupletCarrierId = intent.extras!!.getString(COUPLET_CARRIER_ID)
        startingLetter = intent.extras!!.getString(STARTING_LETTER)
        openCoupletList = intent.extras!!.getBoolean(OPEN_COUPLETLIST)

        couplet_submit.setOnClickListener {
            val couplet = Couplet()
            couplet.id = "${coupletCarrierId}_$coupletsCount"
            val text = couplet_text.text.toString()
            couplet.text = text
            couplet.author = couplet_author.text.toString()
            couplet.startingLetter = startingLetter
            couplet.endingLetter = text[text.length - 1].toString()
            couplet.createdTime = Date()
            couplet.creatorId = appPreff.getUserId()
            couplet.creatorFullname = appPreff.getUserFullname()
            couplet.creatorThumbnailUrl = appPreff.getUserThumbnail()
            couplet.authorId = "rudaki"

            viewModel.saveCouplet(coupletCarrierId!!, coupletsCount, couplet)
                    .addOnSuccessListener {
                        if (openCoupletList){
                            CoupletListActivity.newIntent(this, coupletCarrierId!!)
                        }
                        this.finish()
                    }
        }

    }
}