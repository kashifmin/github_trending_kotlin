package me.kashifminhaj.githubtrending.common

import android.app.Application
import me.kashifminhaj.githubtrending.db.DBHelper

/**
 * Created by kashif on 19/11/17.
 */
class App: Application() {
    companion object {
        lateinit var db: DBHelper
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = DBHelper.getInstance(this)
    }
}