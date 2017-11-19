package me.kashifminhaj.githubtrending.db

/**
 * Created by kashif on 19/11/17.
 */
object Tables {
    object Favorites {
        val NAME = "favorites"
        val COL_ID = "id"
        val COL_NAME = "name"
        val COL_STARS = "stars"
        val COL_URL = "url"
        val COL_OWNER = "owner"
    }

    object Owner {
        val NAME = "owner"
        val COL_LOGIN = "login"
        val COL_AVATAR = "avatar"
        val COL_URL = "url"
    }
}

object DB {
    val VERSION = 1
    val NAME = "trending"
}