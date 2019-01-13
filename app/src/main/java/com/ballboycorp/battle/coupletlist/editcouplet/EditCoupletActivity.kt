package com.ballboycorp.battle.coupletlist.editcouplet

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

        viewModel.getCouplet(getCoupletCarrierId(coupletId!!), coupletId!!)
                .addOnSuccessListener {
                    val couplet = it.toObject(Couplet::class.java)
                    mCouplet = couplet!!
                    couplet_text.setText(couplet.text)
                    couplet_author.setText(couplet.author)
                }

        couplet_submit.setOnClickListener {
            mCouplet.text = couplet_text.text.toString()
            mCouplet.author = couplet_author.text.toString()

            viewModel.saveCouplet(getCoupletCarrierId(coupletId!!),coupletId!!, mCouplet)
                    .addOnSuccessListener {
                        this.finish()
                    }
        }

    }

    private fun getCoupletCarrierId(coupletId: String) : String{
        return coupletId.substring(0, coupletId.indexOf("_"))
    }

    private fun getCoupletNumber(coupletId: String) : String{
        return coupletId.substring(coupletId.indexOf("_")+1)
    }
}