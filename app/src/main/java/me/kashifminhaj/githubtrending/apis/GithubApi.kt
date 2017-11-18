package me.kashifminhaj.githubtrending.apis

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by kashif on 18/11/17.
 */
interface GithubApi {
    @GET("search/repositories")
    fun getTrendingWeekly(
            @Query("q") filter: String,
            @Query("sort") sortBy: String = "stars"
            ) : Observable<Models.TrendingResponse>
    companion object {
        fun create(): GithubApi {
            val baseUrl = "https://api.github.com/"
            val retrofit = Retrofit.Builder()
            retrofit.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            retrofit.addConverterFactory(GsonConverterFactory.create())
            retrofit.baseUrl(baseUrl)
            retrofit.client(OkHttpClient())
            return retrofit.build().create(GithubApi::class.java)
        }
    }

}