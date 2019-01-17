package com.ballboycorp.battle.main.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.ballboycorp.battle.R
import com.ballboycorp.battle.battle.BattleActivity
import com.ballboycorp.battle.main.home.model.Battle
import kotlinx.android.synthetic.main.featured_battle.view.*

/**
 * Created by musooff on 16/01/2019.
 */

class HomeFeaturedAdapter : PagerAdapter() {

    private var battles: List<Battle> = ArrayList()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as RelativeLayout
    }

    override fun getCount(): Int {
        return battles.size
    }

    fun submitList(battles: List<Battle>){
        this.battles = battles
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.featured_battle, container, false)
        view.battle_name.text = battles[position].name
        view.battle_count.text = String.format(view.context.getString(R.string.battle_couplet_count_format), battles[position].coupletCount)
        view.setOnClickListener {
            BattleActivity.newIntent(view.context, battles[position].id!!)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}