package me.kashifminhaj.githubtrending.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import me.kashifminhaj.githubtrending.apis.Models
import org.jetbrains.anko.db.*

/**
 * Created by kashif on 19/11/17.
 */
class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB.NAME, null, DB.VERSION) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null)
                instance = DBHelper(ctx)
            return instance!!
        }

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                Tables.Owner.NAME,
                true,
                Tables.Owner.COL_LOGIN to TEXT + PRIMARY_KEY,
                Tables.Owner.COL_AVATAR to TEXT,
                Tables.Owner.COL_URL to TEXT
        )
        db?.createTable(
                Tables.Favorites.NAME,
                true,
                Tables.Favorites.COL_ID to INTEGER + PRIMARY_KEY,
                Tables.Favorites.COL_NAME to TEXT,
                Tables.Favorites.COL_STARS to INTEGER,
                Tables.Favorites.COL_URL to TEXT,
                Tables.Favorites.COL_OWNER to TEXT,
                Tables.Favorites.COL_LANGUAGE to TEXT,
                Tables.Favorites.COL_DESCRIPTION to TEXT,
                FOREIGN_KEY(Tables.Favorites.COL_OWNER, Tables.Owner.NAME, Tables.Owner.COL_LOGIN)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db?.dropTable(Tables.Favorites.NAME, true)
        db?.dropTable(Tables.Favorites.NAME, true)
        onCreate(db)
    }

    fun getFavoritesAsSet(): HashSet<Long> {
        val values = HashSet<Long>()
        this.readableDatabase.select(Tables.Favorites.NAME, Tables.Favorites.COL_ID)
                .exec {
                    while (moveToNext())
                        values.add(getLong(0))
                }
        return values
    }

    fun setAsFavorite(item: Models.TrendingItem) {
        this.writableDatabase
                .insert(
                        Tables.Owner.NAME,
                        Tables.Owner.COL_LOGIN to item.owner.username,
                        Tables.Owner.COL_URL to item.owner.url,
                        Tables.Owner.COL_AVATAR to item.owner.avatarUrl
                        )
        this.writableDatabase
                .insert(
                        Tables.Favorites.NAME,
                        Tables.Favorites.COL_ID to item.id,
                        Tables.Favorites.COL_NAME to item.fullName,
                        Tables.Favorites.COL_OWNER to item.owner.username,
                        Tables.Favorites.COL_URL to item.url,
                        Tables.Favorites.COL_STARS to item.stars,
                        Tables.Favorites.COL_LANGUAGE to item.language,
                        Tables.Favorites.COL_DESCRIPTION to item.description
                        )
    }

    fun removeFavorite(item: Models.TrendingItem) {
        this.writableDatabase.delete(Tables.Favorites.NAME, "${Tables.Favorites.COL_ID} = ?", arrayOf(item.id.toString()))
        //this.writableDatabase.delete(Tables.Owner.NAME, args = Tables.Owner.COL_LOGIN to item.owner.username)
    }

    fun getFavorites(): List<Models.TrendingItem>? {
        var items: List<Models.TrendingItem>? = null
        val query = "SELECT * FROM %s T1, %s T2 WHERE T1.owner = T2.login".format(Tables.Favorites.NAME, Tables.Owner.NAME)
        val cursor = this.readableDatabase.rawQuery(query, null)
        with(cursor) {
            while (moveToNext()) {

                val parser = TrendingItemParser()
                items = parseList(parser)
            }
            Log.d("DB", "Column count" + columnCount)
            for(c in columnNames)
                Log.d("DB", "Columns" + c)
        }
        return items
    }


}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(getApplicationContext())