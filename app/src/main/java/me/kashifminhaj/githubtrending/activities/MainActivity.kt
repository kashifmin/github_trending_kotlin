package me.kashifminhaj.githubtrending.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.apis.GithubApi
import me.kashifminhaj.githubtrending.fragments.TrendingFragment

class MainActivity : AppCompatActivity() {

    val api: GithubApi by lazy {
        GithubApi.create()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_trending -> {
                fragment = TrendingFragment.newInstance()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}
