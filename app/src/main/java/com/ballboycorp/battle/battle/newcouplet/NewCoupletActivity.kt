package com.ballboycorp.battle.battle.newcouplet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.battle.model.Couplet
import kotlinx.android.synthetic.main.activity_new_couplet.*
import java.util.*

/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletActivity : BaseActivity() {

    companion object {

        private const val COUPLETS_COUNT = "coupletsCount"
        private const val BATTLE_ID = "battleId"
        private const val STARTING_LETTER = "startingLetter"
        private const val SHOULD_OPEN_BATTLE = "shouldOpenBattle"

        fun newIntent(context: Context, battleId: String, coupletsCount: Int, startingLetter: String, shouldOpenBattle: Boolean) {
            val intent = Intent(context, NewCoupletActivity::class.java)
            intent.putExtra(COUPLETS_COUNT, coupletsCount)
            intent.putExtra(BATTLE_ID, battleId)
            intent.putExtra(STARTING_LETTER, startingLetter)
            intent.putExtra(SHOULD_OPEN_BATTLE, shouldOpenBattle)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(NewCoupletViewModel::class.java)
    }

    private var coupletsCount: Int = 0
    private var shouldOpenBattle: Boolean = false
    private var battleId: String? = null
    private var startingLetter: String? = null

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_couplet)

        setTitle(getString(R.string.create_couplet))
        closeBackButton()

        appPreff = AppPreference.getInstance(this)

        coupletsCount = intent.extras!!.getInt(COUPLETS_COUNT)
        battleId = intent.extras!!.getString(BATTLE_ID)
        startingLetter = intent.extras!!.getString(STARTING_LETTER)
        shouldOpenBattle = intent.extras!!.getBoolean(SHOULD_OPEN_BATTLE)

        couplet_submit.setOnClickListener {
            val couplet = Couplet()
            couplet.id = "${battleId}_$coupletsCount"
            val line1 = couplet_line_1.text.toString()
            val line2 = couplet_line_2.text.toString()
            couplet.line1 = line1
            couplet.line2 = line2
            couplet.author = couplet_author.text.toString()
            couplet.startingLetter = startingLetter
            couplet.endingLetter = line2[line2.length - 1].toString().toUpperCase()
            couplet.createdTime = Date()
            couplet.creatorId = appPreff.getUserId()
            couplet.creatorFullname = appPreff.getUserFullname()
            couplet.creatorThumbnailUrl = appPreff.getUserThumbnail()
            couplet.authorId = "rudaki"

            viewModel.saveCouplet(battleId!!, coupletsCount, couplet)
                    .addOnSuccessListener {
                        if (shouldOpenBattle){
                            BattleActivity.newIntent(this, battleId!!)
                        }
                        this.finish()
                    }
        }

    }
}