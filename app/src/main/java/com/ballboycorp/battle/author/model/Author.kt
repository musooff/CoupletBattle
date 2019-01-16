package com.ballboycorp.battle.author.model

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by musooff on 15/01/2019.
 */

class Author() {

    var id: String? = null
    var penName: String? = null
    var name: String? = null
    var coupletCount: Int = 0
    var biography: String? = null
    var thumbnailUrl: String? = null

    companion object {

        fun toAuthorList(documents: List<DocumentSnapshot>): List<Author>{
            val result = ArrayList<Author>()
            documents.forEach {
                val author = it.toObject(Author::class.java)
                result.add(author!!)
            }
            return result
        }
    }
}