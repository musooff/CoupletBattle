package com.ballboycorp.battle.battle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.GlideApp
import com.ballboycorp.battle.R
import com.ballboycorp.battle.author.AuthorActivity
import com.ballboycorp.battle.battle.editcouplet.EditCoupletActivity
import com.ballboycorp.battle.battle.model.Couplet
import com.ballboycorp.battle.common.utils.StringUtils
import com.ballboycorp.battle.user.UserActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_couplet.view.*


/**
 * Created by musooff on 12/01/2019.
 */

class BattleAdapter : RecyclerView.Adapter<BattleAdapter.BattleViewModel>(){

    private var coupletList: List<Couplet> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattleViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_couplet, parent, false)
        return BattleViewModel(view)
    }

    override fun getItemCount(): Int {
        return coupletList.size
    }

    fun submitList(coupletList: List<Couplet>){
        this.coupletList = coupletList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BattleViewModel, position: Int) {
        holder.bind(coupletList[position])
    }

    inner class BattleViewModel(val view: View): RecyclerView.ViewHolder(view){
        fun bind(couplet: Couplet){
            StringUtils.stylizeText(couplet.line1, view.couplet_line_1 as LinearLayout)
            StringUtils.stylizeText(couplet.line2, view.couplet_line_2 as LinearLayout)
            view.couplet_author.text = couplet.authorPenName
            view.couplet_creator_name.text = couplet.creatorName

            GlideApp.with(view.context).load(firebaseStorage.getReference(couplet.creatorThumbnailUrl!!)).into(view.couplet_creator_thumb)

            view.couplet_more.setOnClickListener {
                val moreMenu = PopupMenu(view.context, view.couplet_more)
                moreMenu.inflate(R.menu.more_couplet)
                moreMenu.setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.couplet_edit -> EditCoupletActivity.newIntent(view.context, couplet.id!!, couplet.startingLetter!!, couplet.endingLetter!!)
                    }
                    true
                }

                moreMenu.show()
            }

            view.couplet_user_section.setOnClickListener {
                UserActivity.newIntent(view.context, couplet.creatorId!!)
            }

            view.couplet_author.setOnClickListener {
                AuthorActivity.newIntent(view.context, couplet.authorId!!)
            }
        }
    }

}
