package com.ballboycorp.battle.main.home.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Created by musooff on 12/01/2019.
 */

class CoupletCarrier() {
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

        fun toCoupletList(documents: List<DocumentSnapshot>): List<CoupletCarrier>{
            val result = ArrayList<CoupletCarrier>()
            documents.forEach {
                val coupletCarrier = it.toObject(CoupletCarrier::class.java)
                result.add(coupletCarrier!!)
            }
            return result
        }
    }
}