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
import com.ballboycorp.battle.common.utils.CoupletUtils
import kotlinx.android.synthetic.main.activity_new_couplet.*
import java.util.*
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList


/**
 * Created by musooff on 12/01/2019.
 */

class NewCoupletActivity : BaseActivity() {

    companion object {

        private const val COUPLETS_COUNT = "coupletsCount"
        private const val BATTLE_ID = "battleId"
        private const val STARTING_LETTER = "startingLetter"
        private const val SHOULD_OPEN_BATTLE = "shouldOpenBattle"


        private const val PEN_NAME = "penName"

        fun newIntent(context: Context, battleId: String, coupletsCount: Int, startingLetter: String?, shouldOpenBattle: Boolean) {
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
    private lateinit var battleId: String
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

        coupletsCount = intent.extras!!.getInt(COUPLETS_COUNT)
        battleId = intent.extras!!.getString(BATTLE_ID)!!
        startingLetter = intent.extras!!.getString(STARTING_LETTER)
        shouldOpenBattle = intent.extras!!.getBoolean(SHOULD_OPEN_BATTLE)

        couplet_line_1.hint = CoupletUtils.allowedLetters(startingLetter)

    }


    fun submitCouplet(view: View){

        val line1 = couplet_line_1.text.toString()
        val line2 = couplet_line_2.text.toString()

        if (!CoupletUtils.canSubmit(startingLetter, line1, line2)){
            Snackbar.make(couplet_author, R.string.incorrect_lines, Snackbar.LENGTH_SHORT).show()
            return
        }


        val couplet = Couplet()
        couplet.id = "${battleId}_$coupletsCount"
        couplet.line1 = line1
        couplet.line2 = line2
        val authorPenName = couplet_author.text.toString()
        couplet.author = authorPenName
        couplet.authorId = authorIds[authorPenNames.indexOf(authorPenName)]
        couplet.startingLetter = startingLetter
        couplet.endingLetter = CoupletUtils.getEndingLetter(line2)
        couplet.createdTime = Date()
        couplet.creatorId = appPreff.getUserId()
        couplet.creatorFullname = appPreff.getUserFullname()
        couplet.creatorThumbnailUrl = appPreff.getUserThumbnail()

        viewModel.saveCouplet(battleId, coupletsCount, couplet)
                .addOnSuccessListener {
                    if (shouldOpenBattle){
                        BattleActivity.newIntent(this, battleId)
                    }
                    this.finish()
                }
    }

    override fun onResume() {
        super.onResume()
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
                            if (!authorPenNames.contains(couplet_author.text.toString())){
                                Snackbar.make(couplet_author, R.string.no_author, Snackbar.LENGTH_LONG)
                                        .show()
                            }
                        }
                    }
                }
    }
}