package com.ballboycorp.battle.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ballboycorp.battle.R
import com.ballboycorp.battle.common.base.BaseActivity
import com.ballboycorp.battle.main.home.HomeFragment
import com.ballboycorp.battle.main.me.MeFragment
import com.ballboycorp.battle.main.mybattles.MyBattlesFragment
import com.ballboycorp.battle.main.notification.NotificationFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mFragmentManager: FragmentManager
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customAppBar(my_appbar)

        mFragmentManager = supportFragmentManager
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragment(HomeFragment())

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                loadFragment(MyBattlesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_me ->{
                loadFragment(MeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                loadFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow()
    }

    override fun onBackPressed() {
        if (navigation.selectedItemId != R.id.navigation_home){
            navigation.selectedItemId = R.id.navigation_home
        }
        else{

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

        }
    }
}
