package com.ballboycorp.battle.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.R
import com.ballboycorp.battle.coupletlist.CoupletListActivity
import com.ballboycorp.battle.main.home.model.CoupletCarrier
import kotlinx.android.synthetic.main.item_home_coupletcarriers.view.*


/**
 * Created by musooff on 12/01/2019.
 */

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var coupletCarriers: List<CoupletCarrier> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_coupletcarriers, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coupletCarriers.size
    }

    fun submitList(coupletCarriers: List<CoupletCarrier>){
        this.coupletCarriers = coupletCarriers.sortedByDescending {
            it.coupletCount
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(coupletCarriers[position])
    }

    inner class HomeViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(coupletCarrier: CoupletCarrier){
            view.coupletcarrier_name.text = coupletCarrier.name
            view.coupletcarrier_count.text = "Алъон ${coupletCarrier.coupletCount} байт"

            view.setOnClickListener {
                CoupletListActivity.newIntent(view.context, coupletCarrier.id!!)
            }

            view.coupletcarrier_more.setOnClickListener {
                val moreMenu = PopupMenu(view.context, view.coupletcarrier_more)
                moreMenu.inflate(R.menu.more_couplet)
                moreMenu.setOnMenuItemClickListener {
                    true
                }

                moreMenu.show()
            }
        }
    }

}
