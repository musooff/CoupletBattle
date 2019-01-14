package com.ballboycorp.battle.main.home.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

/**
 * Created by musooff on 12/01/2019.
 */

class Battle() : Serializable{
    var id: String? = null
    var name: String? = null
    var description: String? = null
    @ServerTimestamp
    var createdTime: Date? = null
    var creatorId: String? = null
    var coupletCount: Long = 0
    var thumbnailUrl: String? = null
    var writers: List<String> = arrayListOf()
    var privacy: String? = null

    companion object {

        fun toCoupletList(documents: List<DocumentSnapshot>): List<Battle>{
            val result = ArrayList<Battle>()
            documents.forEach {
                val battle = it.toObject(Battle::class.java)
                result.add(battle!!)
            }
            return result
        }
    }
}