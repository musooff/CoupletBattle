package com.ballboycorp.battle.default

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.R

/**
 * Created by musooff on 13/01/2019.
 */

class DefaultFragment : Fragment(){

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(DefaultViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}