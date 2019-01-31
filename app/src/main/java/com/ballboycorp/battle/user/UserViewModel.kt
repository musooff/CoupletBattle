package com.ballboycorp.battle.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballboycorp.battle.battle.newcouplet.model.Post
import com.ballboycorp.battle.main.notification.model.Notification
import com.ballboycorp.battle.network.FirebaseService
import com.ballboycorp.battle.network.StorageService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by musooff on 13/01/2019.
 */

class UserViewModel : ViewModel() {

    private val firebaseService = FirebaseService()

    private val storageService = StorageService()

    val userPosts: MutableLiveData<List<Post>> = MutableLiveData()

    fun getUser(userId: String): Task<DocumentSnapshot> {
        return firebaseService.userRef(userId)
                .get()
    }

    fun getImageUrl(imageUrl: String) = storageService.getImageRef(imageUrl)

    fun sendFriendRequest(userId: String, notification: Notification): Task<DocumentReference> {
        return firebaseService.sendFriendRequest(userId, notification)

    }

    fun acceptFriendRequest(userId: String, notification: Notification) : Task<DocumentReference>{
        return firebaseService.acceptFriendRequest(userId, notification)
    }

    fun getUserPosts(userId: String){
        firebaseService.postsRef(userId)
                .get()
                .addOnSuccessListener {
                    userPosts.postValue(Post.toPostList(it.documents))
                }
    }

}