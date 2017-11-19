package me.kashifminhaj.githubtrending.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
                FOREIGN_KEY(Tables.Favorites.COL_OWNER, Tables.Owner.NAME, Tables.Owner.COL_LOGIN)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db?.dropTable(Tables.Favorites.NAME, true)
        db?.dropTable(Tables.Favorites.NAME, true)
        onCreate(db)
    }

}