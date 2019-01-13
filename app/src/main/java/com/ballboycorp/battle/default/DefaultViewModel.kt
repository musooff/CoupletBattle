package com.ballboycorp.battle.default

import androidx.lifecycle.ViewModel

/**
 * Created by musooff on 13/01/2019.
 */

class DefaultViewModel : ViewModel() {

    private val repository = DefaultRepository()

    fun getCoupletCarriers(){
        repository.getCoupletCarriersRef()
    }
}