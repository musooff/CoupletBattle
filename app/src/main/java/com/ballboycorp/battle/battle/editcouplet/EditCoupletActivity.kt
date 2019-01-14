package com.ballboycorp.battle.battle.editcouplet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.common.preference.AppPreference
import com.ballboycorp.battle.battle.model.Couplet
import kotlinx.android.synthetic.main.activity_editcouplet.*

/**
 * Created by musooff on 12/01/2019.
 */

class EditCoupletActivity : BaseActivity() {

    companion object {

        private const val COUPLET_ID = "coupletId"
        private const val STARTING_LETTER = "startingLetter"
        private const val ENDING_LETTER = "endingLetter"

        fun newIntent(context: Context, coupletId: String, startingLetter: String, endingLetter: String) {
            val intent = Intent(context, EditCoupletActivity::class.java)
            intent.putExtra(COUPLET_ID, coupletId)
            intent.putExtra(STARTING_LETTER, startingLetter)
            intent.putExtra(ENDING_LETTER, endingLetter)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(EditCoupletViewModel::class.java)
    }

    private var coupletId: String? = null
    private var startingLetter: String? = null
    private lateinit var mCouplet: Couplet

    private lateinit var appPreff: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcouplet)

        appPreff = AppPreference.getInstance(this)

        coupletId = intent.extras!!.getString(COUPLET_ID)
        startingLetter = intent.extras!!.getString(STARTING_LETTER)

        viewModel.getCouplet(getBattleId(coupletId!!), coupletId!!)
                .addOnSuccessListener {
                    val couplet = it.toObject(Couplet::class.java)
                    mCouplet = couplet!!
                    couplet_line_1.setText(couplet.line1)
                    couplet_line_2.setText(couplet.line2)
                    couplet_author.setText(couplet.author)
                }

        couplet_submit.setOnClickListener {
            mCouplet.line1 = couplet_line_1.text.toString()
            mCouplet.line2 = couplet_line_2.text.toString()
            mCouplet.author = couplet_author.text.toString()

            viewModel.saveCouplet(getBattleId(coupletId!!),coupletId!!, mCouplet)
                    .addOnSuccessListener {
                        this.finish()
                    }
        }

    }

    private fun getBattleId(coupletId: String) : String{
        return coupletId.substring(0, coupletId.indexOf("_"))
    }

    private fun getCoupletNumber(coupletId: String) : String{
        return coupletId.substring(coupletId.indexOf("_")+1)
    }
}