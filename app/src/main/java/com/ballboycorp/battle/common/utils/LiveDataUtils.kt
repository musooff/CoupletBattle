package com.ballboycorp.battle.common.utils

import androidx.lifecycle.MutableLiveData
import com.ballboycorp.battle.user.model.User

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

fun MutableLiveData<List<User>>.addIfNotExists(item: User) {
    if (this.value == null){
        this.value = arrayListOf(item)
        return
    }
    val updatedItems = this.value as ArrayList

    updatedItems.forEach {
        if (it.id == item.id){
            return
        }
    }
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