package com.ballboycorp.battle.battle.newcouplet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.battle.newcouplet.model.Input
import com.ballboycorp.battle.common.utils.ButtonUtils
import com.ballboycorp.battle.common.utils.CoupletUtils
import com.ballboycorp.battle.main.home.model.Battle
import kotlinx.android.synthetic.main.activity_new_couplet.*
import java.util.*
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList


/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletActivity : BaseActivity() {

    companion object {

        private const val BATTLE = "battle"
        private const val STARTING_LETTER = "startingLetter"
        private const val SHOULD_OPEN_BATTLE = "shouldOpenBattle"


        private const val PEN_NAME = "penName"

        fun newIntent(context: Context, battle: Battle, startingLetter: String?, shouldOpenBattle: Boolean) {
            val intent = Intent(context, NewCoupletActivity::class.java)
            intent.putExtra(BATTLE, battle)
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

    private var coupletsCount: Long = 0
    private var shouldOpenBattle: Boolean = false
    private lateinit var battle: Battle
    private var startingLetter: String? = null

    private var authorIds = ArrayList<String>()
    private var authorPenNames = ArrayList<String>()
    private lateinit var authorArrayAdapter: ArrayAdapter<String>

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_couplet)

        setTitle(getString(R.string.title_create_couplet))
        enableBackButton(true)

        appPreff = AppPreference.getInstance(this)

        battle = intent.extras!!.getSerializable(BATTLE) as Battle
        coupletsCount = battle.coupletCount
        startingLetter = intent.extras!!.getString(STARTING_LETTER)
        shouldOpenBattle = intent.extras!!.getBoolean(SHOULD_OPEN_BATTLE)

        couplet_line_1.hint = CoupletUtils.firstLineHint(startingLetter)

        ButtonUtils.invalidateButton(button_submit, getString(R.string.button_submit), null, true)

        viewModel.getAuthors()
                .addOnSuccessListener {
                    it.documents.forEach {
                        authorIds.add(it.id)
                        authorPenNames.add(it.getString(PEN_NAME)!!)
                    }

                    authorArrayAdapter = ArrayAdapter(this, android.R.layout.select_dialog_item, authorPenNames)
                    couplet_author.setAdapter(authorArrayAdapter)
                    couplet_author.threshold = 1
                    couplet_author.setOnFocusChangeListener { v, hasFocus ->
                        if (!hasFocus) {
                            if (!authorPenNames.contains(couplet_author.text.toString())) {
                                Snackbar.make(couplet_author, R.string.no_author, Snackbar.LENGTH_LONG)
                                        .show()
                            }
                        }
                    }
                }
        button_submit.setOnClickListener {
            submitCouplet(it)
        }

    }


    private fun submitCouplet(view: View) {

        val line1 = couplet_line_1.text.toString()
        val line2 = couplet_line_2.text.toString()
        val authorPenName = couplet_author.text.toString()

        val input = CoupletUtils.canSubmit(startingLetter, line1, line2, authorPenName, authorPenNames)

        if (!input.isCorrect) {
            var solution = input.solution
            if (input == Input.WRONG_STARTING){
                solution = String.format(solution, CoupletUtils.allowedLetters(startingLetter))
            }
            Snackbar.make(couplet_author, solution, Snackbar.LENGTH_LONG).show()
            return
        }


        val couplet = Couplet()
        couplet.id = "${battle.id}_$coupletsCount"
        couplet.battleId = battle.id
        couplet.battleName = battle.name
        couplet.line1 = line1
        couplet.line2 = line2
        couplet.authorPenName = authorPenName
        couplet.authorId = authorIds[authorPenNames.indexOf(authorPenName)]
        couplet.startingLetter = startingLetter
        couplet.endingLetter = CoupletUtils.getEndingLetter(line2)
        couplet.createdTime = Date()
        couplet.creatorId = appPreff.getUserId()
        couplet.creatorName = appPreff.getUserName()
        couplet.creatorThumbnailUrl = appPreff.getUserThumbnail()

        viewModel.saveCouplet(battle, couplet)
                .addOnSuccessListener {
                    if (shouldOpenBattle) {
                        BattleActivity.newIntent(this, battle.id!!)
                    }
                    this.finish()
                }
    }
}