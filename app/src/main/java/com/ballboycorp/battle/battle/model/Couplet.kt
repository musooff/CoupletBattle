package com.ballboycorp.battle.battle.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by musooff on 12/01/2019.
 */

class Couplet() {

    var id: String? = null
    var line1: String? = null
    var line2: String? = null
    var creatorId: String? = null
    var creatorFullname: String? = null
    var creatorThumbnailUrl: String? = null
    @ServerTimestamp
    var createdTime: Date? = null
    var author: String? = null
    var authorId: String? = null
    var startingLetter: String? = null
    var endingLetter: String? = null

    companion object {

        fun toCoupletList(documents: List<DocumentSnapshot>): List<Couplet>{
            val result = ArrayList<Couplet>()
            documents.forEach {
                val couplet = it.toObject(Couplet::class.java)
                result.add(couplet!!)
            }
            return result
        }
    }
}