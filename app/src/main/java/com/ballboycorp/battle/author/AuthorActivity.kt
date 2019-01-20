package com.ballboycorp.battle.author

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.model.Author
import com.ballboycorp.battle.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_author.*

/**
 * Created by musooff on 13/01/2019.
 */

class AuthorActivity : BaseActivity() {

    companion object {
        private const val AUTHOR_ID = "authorId"

        fun newIntent(context: Context, authorId: String) {
            val intent = Intent(context, AuthorActivity::class.java)
            intent.putExtra(AUTHOR_ID, authorId)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(AuthorViewModel::class.java)
    }

    private lateinit var authorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)

        customAppBar(my_appbar)
        enableBackButton()


        authorId = intent.extras!!.getString(AUTHOR_ID)!!

        viewModel.getAuthor(authorId)
                .get()
                .addOnSuccessListener {
                    val author = it.toObject(Author::class.java)

                    author_name.text = author!!.name
                    author_couplet_count.text = author.coupletCount.toString()
                    author_biography.text = author.biography

                    GlideApp.with(this).load(viewModel.getImageRef(author.thumbnailUrl!!)).into(author_thumb)
                }
    }

}