package com.ballboycorp.battle.network

import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.battle.newcouplet.model.Post
import com.ballboycorp.battle.main.home.model.Battle
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.user.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by musooff on 31/01/2019.
 */

class FirebaseService {

    companion object {

        private const val USERS_REF = "users"
        private const val BATTLES_REF = "battles"
        private const val AUTHORS_REF = "authors"
        private const val NOTIFICATIONS_REF = "notifications"
        private const val COUPLETS_REF = "couplets"
        private const val POSTS_REF = "posts"


        private const val FOLLOWERS = "followers"
        private const val WRITERS = "writers"
        private const val REQUESTED_WRITERS = "requestedWriters"
        private const val IS_FEATURED = "featured"
        private const val HAS_FEATURED_COUPLET = "hasFeaturedCouplet"
        private const val COUPLET_ID = "id"
        private const val NOTIFICATION_ID = "id"
        private const val BATTLE_ID = "id"
        private const val USER_ID = "id"
        private const val AUTHOR_ID = "id"
        private const val POSTS_ID = "id"
        private const val COUPLET_COUNT = "coupletCount"
        private const val FRIENDS_PENDING_FROM = "friendsPendingFrom"
        private const val FRIENDS_PENDING_TO = "friendsPendingTo"
        private const val FRIENDS_LIST = "friendList"
        private const val FRIEND_COUNT = "friendCount"
        private const val USER_EMAIL = "email"
        private const val USER_PHONE_NUMBER = "phoneNumber"



    }

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun usersRef() = firebaseDatabase.collection(USERS_REF)

    fun userRef(userId: String) = usersRef().document(userId)

    fun userRefByEmail(email: String) = usersRef()
            .whereEqualTo(USER_EMAIL, email)

    fun userRefByPhoneNumber(phoneNumber: String) = usersRef()
            .whereEqualTo(USER_PHONE_NUMBER, phoneNumber)

    fun addUser(user: User) = usersRef()
            .add(user)
            .addOnSuccessListener {
                it.update(USER_ID, it.id)
            }

    fun battlesRef() = firebaseDatabase.collection(BATTLES_REF)

    fun myBattlersRef(userId: String) = battlesRef().whereArrayContains(FOLLOWERS, userId)

    fun featuredBattlesRef() = battlesRef().whereEqualTo(IS_FEATURED, true)

    fun battlesWithFeaturedCoupletRef() = battlesRef().whereEqualTo(HAS_FEATURED_COUPLET, true)

    fun battleRef(battleId: String) = battlesRef().document(battleId)

    fun addBattle(battle: Battle) = battlesRef().add(battle)
            .addOnSuccessListener {
                it.update(BATTLE_ID, it.id)
            }

    fun followBattle(battleId: String, userId: String) = battleRef(battleId)
            .update(FOLLOWERS, FieldValue.arrayUnion(userId))

    fun unFollowBattle(battleId: String, userId: String) = battleRef(battleId)
            .update(FOLLOWERS, FieldValue.arrayRemove(userId))

    fun requestJoinBattle(battleId: String, userId: String) = battleRef(battleId)
            .update(REQUESTED_WRITERS, FieldValue.arrayUnion(userId))

    fun authorsRef() = firebaseDatabase.collection(AUTHORS_REF)

    fun authorRef(authorId: String) = authorsRef().document(authorId)

    fun incrementAuthorCoupletCount(authorId: String, amount: Int) = authorRef(authorId)
            .get()
            .addOnSuccessListener {
                authorRef(authorId)
                        .update(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(amount))
            }

    fun postsRef(userId: String) = userRef(userId).collection(POSTS_REF)

    fun addPost(userId: String, post: Post) = postsRef(userId)
            .add(post)
            .addOnSuccessListener {
                postsRef(userId)
                        .document(it.id)
                        .update(POSTS_ID, it.id)
            }

    fun notificationsRef(userId: String) = userRef(userId).collection(NOTIFICATIONS_REF)

    fun notificationRef(userId: String, notificationId: String) = notificationsRef(userId).document(notificationId)

    fun addNotification(userId: String, notification: Notification) = notificationsRef(userId).add(notification)
            .addOnSuccessListener {
                notificationRef(userId, it.id)
                        .update(NOTIFICATION_ID, it.id)
            }

    fun coupletsRef(battleId: String) = battleRef(battleId).collection(COUPLETS_REF)

    fun coupletRef(battleId: String, coupletId: String) = coupletsRef(battleId).document(coupletId)

    fun featuredCoupletRef(battleId: String, coupletId: String) = coupletsRef(battleId).whereEqualTo(COUPLET_ID, coupletId)

    fun addCouplet(battle: Battle, couplet: Couplet) = coupletsRef(battle.id!!)
            .document("${battle.id}_${battle.coupletCount}")
            .set(couplet)
            .addOnSuccessListener {

                battleRef(battle.id!!)
                        .update(COUPLET_COUNT, (battle.coupletCount + 1))

                userRef(couplet.creatorId!!)
                        .get()
                        .addOnSuccessListener {
                            userRef(couplet.creatorId!!)
                                    .update(COUPLET_COUNT, it.getLong(COUPLET_COUNT)!!.plus(1))
                        }

                addPost(couplet.creatorId!!, Post.toPost(battle, couplet))

                battleRef(battle.id!!)
                        .get()
                        .addOnSuccessListener {
                            val writers = it.get(WRITERS) as List<*>
                            val followers = it.get(FOLLOWERS) as List<*>
                            if (!writers.contains(couplet.creatorId)){
                                battleRef(battle.id!!)
                                        .update(WRITERS, FieldValue.arrayUnion(couplet.creatorId))
                            }
                            if (!followers.contains(couplet.creatorId)){
                                battleRef(battle.id!!)
                                        .update(FOLLOWERS, FieldValue.arrayUnion(couplet.creatorId))
                            }
                        }

                incrementAuthorCoupletCount(couplet.authorId!!, 1)
            }

    fun sendFriendRequest(userId: String, notification: Notification) =
            addNotification(userId, notification)
                    .addOnSuccessListener {
                        userRef(notification.fromUserId!!)
                                .update(FRIENDS_PENDING_FROM, FieldValue.arrayUnion(userId))
                        userRef(userId)
                                .update(FRIENDS_PENDING_TO, FieldValue.arrayUnion(notification.fromUserId))
                    }

    fun acceptFriendRequest(userId: String, notification: Notification) =
            addNotification(userId, notification)
                    .addOnSuccessListener {
                        userRef(notification.fromUserId!!)
                                .update(FRIENDS_PENDING_TO, FieldValue.arrayRemove(userId))

                        userRef(userId)
                                .update(FRIENDS_PENDING_FROM, FieldValue.arrayRemove(notification.fromUserId))

                        userRef(notification.fromUserId!!)
                                .update(FRIENDS_LIST, FieldValue.arrayUnion(userId))

                        userRef(userId)
                                .update(FRIENDS_LIST, FieldValue.arrayUnion(notification.fromUserId))
                    }
                    .addOnSuccessListener {
                        increaseFriendCount(userId)
                        increaseFriendCount(notification.fromUserId!!)
                    }

    private fun increaseFriendCount(userId: String){
        userRef(userId)
                .get()
                .addOnSuccessListener {
                    userRef(userId)
                            .update(FRIEND_COUNT, it.getLong(FRIEND_COUNT)!!.plus(1))
                }
    }

}