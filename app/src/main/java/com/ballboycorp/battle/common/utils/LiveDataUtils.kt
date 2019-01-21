package com.ballboycorp.battle.common.utils

import androidx.lifecycle.MutableLiveData

/**
 * Created by musooff on 17/01/2019.
 */

fun <T> MutableLiveData<List<T>>.add(item: T) {
    if (this.value == null){
        this.value = arrayListOf(item)
        return
    }
    val updatedItems = this.value as ArrayList
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.addAll(items: List<T>) {
    if (this.value == null){
        this.value = items
        return
    }
    val updatedItems = this.value as ArrayList
    updatedItems.addAll(items)
    this.value = updatedItems
}