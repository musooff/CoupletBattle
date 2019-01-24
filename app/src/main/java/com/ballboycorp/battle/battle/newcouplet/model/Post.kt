package com.ballboycorp.battle.battle.newcouplet.model

import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.main.home.model.Battle
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by musooff on 24/01/2019.
 */

class Post {
    var authorId: String? = null
    var authorPenName: String? = null
    var battleId: String? = null
    var battleName: String? = null
    var coupletId: String? = null
    var line1: String? = null
    var line2: String? = null

    companion object {

        fun toPost(battle: Battle, couplet: Couplet): Post{
            val result = Post()
            result.authorId = couplet.authorId
            result.authorPenName = couplet.authorPenName
            result.coupletId = couplet.id
            result.line1 = couplet.line1
            result.line2 = couplet.line2
            result.battleId = battle.id
            result.battleName = battle.name
            return result
        }

        fun toPostList(documents: List<DocumentSnapshot>): List<Post>{
            val result = ArrayList<Post>()
            documents.forEach {
                val single = it.toObject(Post::class.java)
                result.add(single!!)
            }
            return result
        }
    }
}