package me.kashifminhaj.githubtrending.db

import me.kashifminhaj.githubtrending.apis.Models
import org.jetbrains.anko.db.MapRowParser

/**
 * Created by kashif on 19/11/17.
 */
class TrendingItemParser: MapRowParser<Models.TrendingItem> {
    override fun parseRow(columns: Map<String, Any?>): Models.TrendingItem {

        val owner = Models.Owner(
                columns[Tables.Owner.COL_LOGIN].toString(),
                columns[Tables.Owner.COL_URL].toString(),
                columns[Tables.Owner.COL_AVATAR].toString()
        )

        val item = Models.TrendingItem(
                columns[Tables.Favorites.COL_ID].toString().toLong(),
                columns[Tables.Favorites.COL_NAME].toString(),
                columns[Tables.Favorites.COL_URL].toString(),
                columns[Tables.Favorites.COL_STARS].toString().toInt(),
                columns[Tables.Favorites.COL_DESCRIPTION].toString(),
                columns[Tables.Favorites.COL_LANGUAGE].toString(),
                owner,
                true
        )
        return item
    }

}