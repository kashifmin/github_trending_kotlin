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
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.adapters.TrendingListAdapter
import me.kashifminhaj.githubtrending.apis.GithubApi
import me.kashifminhaj.githubtrending.apis.Models
import me.kashifminhaj.githubtrending.db.database
import me.kashifminhaj.githubtrending.extensions.asFormat
import me.kashifminhaj.githubtrending.extensions.toLastMonth
import me.kashifminhaj.githubtrending.extensions.toLastWeek
import me.kashifminhaj.githubtrending.extensions.toYesterday
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [TrendingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrendingFragment : Fragment(), TrendingListAdapter.OnFavoriteToggleListener, AdapterView.OnItemSelectedListener {

    val api: GithubApi by lazy {
        GithubApi.create()
    }
    var recylerView: RecyclerView? = null
    var adapter: TrendingListAdapter? = null
    var data: List<Models.TrendingItem>? = null

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_trending, container, false)
        recylerView = v?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        val spinner: Spinner? = v?.find(R.id.spinnerFrom)
        spinner?.onItemSelectedListener = this
        adapter = TrendingListAdapter(data, this)
        recylerView?.layoutManager = LinearLayoutManager(context)
        recylerView?.adapter = adapter
        //if(data == null) getTrending()
        return v
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    fun getTrending(dateStr: String) {
        Log.d("getTrending", "Trending for date: " + dateStr)
        disposable.add(api.getTrendingWeekly("created:>" + dateStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.d("trending", "got reponse :) count: " + it.items.count())
                            filterFavorites(it.items)
                            data = it.items
                            adapter?.data = it.items
                            adapter?.notifyDataSetChanged()
                        },
                        {
                            showStatus(it.localizedMessage)
                        }
                ))
    }

    fun getTrendingWeekly(){
        val date = Date().toLastWeek().asFormat("yyyy-MM-dd")
        getTrending(date)
    }
    fun getTrendingMonthly(){
        val date = Date().toLastMonth().asFormat("yyyy-MM-dd")
        getTrending(date)
    }
    fun getTrendingToday(){
        val date = Date().toYesterday().asFormat("yyyy-MM-dd")
        getTrending(date)
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
        when(pos) {
            0 -> getTrendingToday()
            1 -> getTrendingWeekly()
            2 -> getTrendingMonthly()
        }
    }

    companion object {
        fun newInstance(): TrendingFragment {
            val fragment = TrendingFragment()
            return fragment
        }
    }

}