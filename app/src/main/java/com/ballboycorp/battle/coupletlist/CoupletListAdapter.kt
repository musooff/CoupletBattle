package com.ballboycorp.battle.coupletlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.R
import com.ballboycorp.battle.coupletlist.model.Couplet
import com.ballboycorp.battle.user.UserActivity
import kotlinx.android.synthetic.main.item_couplelist.view.*


/**
 * Created by musooff on 12/01/2019.
 */

class CoupletListAdapter : RecyclerView.Adapter<CoupletListAdapter.CoupletListViewHolder>(){

    private var coupletList: List<Couplet> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoupletListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_couplelist, parent, false)
        return CoupletListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coupletList.size
    }

    fun submitList(coupletList: List<Couplet>){
        this.coupletList = coupletList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CoupletListViewHolder, position: Int) {
        holder.bind(coupletList[position])
    }

    inner class CoupletListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(couplet: Couplet){
            view.couplet_text.text = couplet.text!!.replace("\\n", "\n")
            view.couplet_author.text = couplet.author
            view.couplet_creator_fullname.text = couplet.creatorFullname

            view.couplet_more.setOnClickListener {
                val moreMenu = PopupMenu(view.context, view.couplet_more)
                moreMenu.inflate(R.menu.more_couplet)
                moreMenu.setOnMenuItemClickListener {
                    true
                }

                moreMenu.show()
            }

            view.couplet_user_section.setOnClickListener {
                UserActivity.newIntent(view.context, couplet.creatorId!!)
            }
        }
    }

}
