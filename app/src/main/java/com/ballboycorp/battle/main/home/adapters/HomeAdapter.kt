package com.ballboycorp.battle.main.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballboycorp.battle.R
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.main.home.model.Battle
import kotlinx.android.synthetic.main.item_home_battle.view.*


/**
 * Created by musooff on 12/01/2019.
 */

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var battles: List<Battle> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_battle, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return battles.size
    }

    fun isEmpty(): Boolean {
        return battles.isEmpty()
    }

    fun submitList(battles: List<Battle>){
        this.battles = battles.sortedByDescending {
            it.coupletCount
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(battles[position])
    }

    inner class HomeViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(battle: Battle){
            view.battle_name.text = battle.name
            view.battle_count.text = String.format(view.context.getString(R.string.couple_count), battle.coupletCount)

            view.setOnClickListener {
                BattleActivity.newIntent(view.context, battle.id!!)
            }
        }
    }

}
