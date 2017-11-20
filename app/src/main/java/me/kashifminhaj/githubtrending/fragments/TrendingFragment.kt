package me.kashifminhaj.githubtrending.fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.adapters.TrendingListAdapter
import me.kashifminhaj.githubtrending.apis.GithubApi
import me.kashifminhaj.githubtrending.apis.Models
import me.kashifminhaj.githubtrending.db.database
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread


/**
 * A simple [Fragment] subclass.
 * Use the [TrendingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrendingFragment : Fragment(), TrendingListAdapter.OnFavoriteToggleListener {

    val api: GithubApi by lazy {
        GithubApi.create()
    }
    var recylerView: RecyclerView? = null
    var adapter: TrendingListAdapter? = null
    var data: List<Models.TrendingItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_trending, container, false)
        recylerView = v?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        adapter = TrendingListAdapter(data, this)
        recylerView?.layoutManager = LinearLayoutManager(context)
        recylerView?.adapter = adapter
        if(data == null) getTrending()
        return v
    }

    fun getTrending() {
        api.getTrendingWeekly("created:>2017-11-17")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("trending", "got reponse :)")
                    filterFavorites(it.items)
                    data = it.items
                    adapter?.data = it.items
                    adapter?.notifyDataSetChanged()
                }
    }

    fun filterFavorites(items: List<Models.TrendingItem>?) {
        val favs = this.context.database.getFavoritesAsSet()
        items?.map { it.isFavorite = it.id in favs }
    }

    override fun onToggle(item: Models.TrendingItem, btn: ImageView) {
        if(!item.isFavorite)
            doAsync {
                context.database.setAsFavorite(item)
                uiThread { item.isFavorite = true; showStatus("Marked as favorite"); adapter?.notifyDataSetChanged() }
            }
        else
            doAsync {
                context.database.removeFavorite(item)
                uiThread { item.isFavorite = false; showStatus("Removed favorite"); adapter?.notifyDataSetChanged() }
            }

    }

    fun showStatus(msg: String) {
        Snackbar.make(activity.find(R.id.contentFrame), msg, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): TrendingFragment {
            val fragment = TrendingFragment()
            return fragment
        }
    }

}