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
            @SerializedName("language") val language: String,
            @SerializedName("owner") val owner: Owner
    )
    data class TrendingResponse(
            @SerializedName("total_count") val totalCount: String,
            @SerializedName("items") val items: List<TrendingItem>
    )

    data class Owner(
            @SerializedName("login") val username: String,
            @SerializedName("url") val url: String,
            @SerializedName("avatar_url") val avatarUrl: String
    )
}