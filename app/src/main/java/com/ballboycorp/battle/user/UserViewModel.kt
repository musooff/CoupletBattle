package com.ballboycorp.battle.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.newcouplet.model.Post
import com.ballboycorp.battle.main.notification.model.Notification
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue

/**
 * Created by musooff on 13/01/2019.
 */

class UserViewModel : ViewModel() {

    companion object {
        private const val NOTIFICATION_ID = "id"
        private const val FRIENDS_PENDING_FROM = "friendsPendingFrom"
        private const val FRIENDS_PENDING_TO = "friendsPendingTo"
        private const val FRIENDS_LIST = "friendList"
        private const val FRIEND_COUNT = "friendCount"
    }

    private val repository = UserRepository()

    val userPosts: MutableLiveData<List<Post>> = MutableLiveData()

    fun getUser(userId: String): DocumentReference {
        return repository.getUser(userId)
    }

    fun getImageUrl(imageUrl: String) = repository.getImageRef(imageUrl)

    private fun createNotification(userId: String, notification: Notification): Task<DocumentReference>{
        return repository.getNotificationRef(userId)
                .add(notification)
                .addOnSuccessListener {
                    val notificationId = it.id
                    repository.getNotificationRef(userId)
                            .document(notificationId)
                            .update(NOTIFICATION_ID, notificationId)
                }
    }

    fun sendFriendRequest(userId: String, notification: Notification): Task<DocumentReference> {
        return createNotification(userId, notification)
                .addOnSuccessListener {
                    repository.getUser(notification.fromUserId!!)
                            .update(FRIENDS_PENDING_FROM, FieldValue.arrayUnion(userId))
                    repository.getUser(userId)
                            .update(FRIENDS_PENDING_TO, FieldValue.arrayUnion(notification.fromUserId))
                }

    }

    fun acceptFriendRequest(userId: String, notification: Notification) : Task<DocumentReference>{
        return createNotification(userId, notification)
                .addOnSuccessListener {
                    repository.getUser(notification.fromUserId!!)
                            .update(FRIENDS_PENDING_TO, FieldValue.arrayRemove(userId))
                    repository.getUser(userId)
                            .update(FRIENDS_PENDING_FROM, FieldValue.arrayRemove(notification.fromUserId))

                    repository.getUser(notification.fromUserId!!)
                            .update(FRIENDS_LIST, FieldValue.arrayUnion(userId))
                    repository.getUser(userId)
                            .update(FRIENDS_LIST, FieldValue.arrayUnion(notification.fromUserId!!))
                }
                .addOnSuccessListener {
                    increaseFriendCount(userId)
                    increaseFriendCount(notification.fromUserId!!)
                }
    }

    private fun increaseFriendCount(userId: String){
        repository.getUser(userId)
                .get()
                .addOnSuccessListener {
                    val friendCount = it.getLong(FRIEND_COUNT)
                    repository.getUser(userId)
                            .update(FRIEND_COUNT, friendCount!! + 1)
                }
    }

    fun getUserPosts(userId: String){
        repository.getPosts(userId)
                .get()
                .addOnSuccessListener {
                    userPosts.postValue(Post.toPostList(it.documents))
                }
    }

}