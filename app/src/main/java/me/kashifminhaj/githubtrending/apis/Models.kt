package me.kashifminhaj.githubtrending.apis

import com.google.gson.annotations.SerializedName

/**
 * Created by kashif on 18/11/17.
 */
object Models {
    data class TrendingItem(
            @SerializedName("full_name") val fullName: String,
            @SerializedName("url") val url: String,
            @SerializedName("stargazers_count") val stars: Int,
            @SerializedName("description") val description: String?,
            @SerializedName("language") val language: String
    )
    data class TrendingResponse(
            @SerializedName("total_count") val totalCount: String,
            @SerializedName("items") val items: List<TrendingItem>
            )
}