package com.ballboycorp.battle.main.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.AuthorActivity
import com.ballboycorp.battle.author.model.Author
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_author.view.*

/**
 * Created by musooff on 16/01/2019.
 */

class HomeAuthorsAdapter : RecyclerView.Adapter<HomeAuthorsAdapter.HomeAuthorsViewHolder>() {

    private var authors: List<Author> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAuthorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false)
        return HomeAuthorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    fun submitList(authors: List<Author>){
        this.authors = authors.sortedByDescending {
            it.coupletCount
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeAuthorsViewHolder, position: Int) {
        holder.bind(authors[position])
    }

    inner class HomeAuthorsViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(author: Author){
            view.author_pen_name.text = author.penName
            view.author_couplet_count.text = String.format(view.context.getString(R.string.couplet_count_format), author.coupletCount)
            view.setOnClickListener {
                AuthorActivity.newIntent(view.context, author.id!!)
            }
            GlideApp.with(view).load(firebaseStorage.getReference(author.thumbnailUrl!!)).into(view.author_thumb)
        }
    }
}