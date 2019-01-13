package com.ballboycorp.battle.common.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ballboycorp.battle.R

/**
 * Created by musooff on 12/01/2019.
 */

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun closeBackButton(){
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
    }

    fun setTitle(title: String){
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}