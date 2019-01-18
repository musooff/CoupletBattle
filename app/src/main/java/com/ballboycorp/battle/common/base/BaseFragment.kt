package com.ballboycorp.battle.common.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ballboycorp.battle.R

/**
 * Created by musooff on 12/01/2019.
 */

open class BaseFragment : Fragment() {

    fun setTitle(title: String){
        (activity as AppCompatActivity).title = title
    }

    fun customAppBar(view: View){
        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.my_toolbar))
    }
}