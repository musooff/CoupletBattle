package com.ballboycorp.battle.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.AuthorActivity
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.battle.newcouplet.model.Post
import com.ballboycorp.battle.common.utils.StringUtils
import com.ballboycorp.battle.user.model.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_post.view.*

/**
 * Created by musooff on 21/01/2019.
 */

class PostAdapter(private val user: User) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    private var posts: List<Post> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun submitList(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(post: Post){
            view.couplet_creator_name.text = user.name
            view.couplet_author.text = post.authorPenName
            view.couplet_battle_name.text = post.battleName

            StringUtils.stylizeText(post.line1, view.couplet_line_1 as LinearLayout)
            StringUtils.stylizeText(post.line2, view.couplet_line_2 as LinearLayout)

            GlideApp.with(view.context).load(firebaseStorage.getReference(user.thumbnailUrl!!)).into(view.couplet_creator_thumb)

            view.couplet_battle_name.setOnClickListener {
                BattleActivity.newIntent(view.context, post.battleId!!)
            }

            view.couplet_author.setOnClickListener {
                AuthorActivity.newIntent(view.context, post.authorId!!)
            }
        }
    }
}