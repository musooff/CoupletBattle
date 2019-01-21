package com.ballboycorp.battle.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.R
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.common.utils.StringUtils
import kotlinx.android.synthetic.main.item_post.view.*

/**
 * Created by musooff on 21/01/2019.
 */

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    private var posts: List<Couplet> = ArrayList()

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

    fun submitList(posts: List<Couplet>){
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(couplet: Couplet){
            view.couplet_creator_name.text = couplet.creatorName
            view.couplet_author.text = couplet.author

            StringUtils.stylizeText(couplet.line1, view.couplet_line_1 as LinearLayout)
            StringUtils.stylizeText(couplet.line2, view.couplet_line_2 as LinearLayout)
        }
    }
}