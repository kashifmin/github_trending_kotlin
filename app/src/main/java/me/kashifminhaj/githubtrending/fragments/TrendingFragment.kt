package me.kashifminhaj.githubtrending.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.adapters.TrendingListAdapter
import me.kashifminhaj.githubtrending.apis.GithubApi


/**
 * A simple [Fragment] subclass.
 * Use the [TrendingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrendingFragment : Fragment() {

    val api: GithubApi by lazy {
        GithubApi.create()
    }
    var recylerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recylerView = view?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        getTrending()
    }

    fun getTrending() {
        api.getTrendingWeekly("created:>2017-11-17")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("trending", "got reponse :)")
                    recylerView?.adapter = TrendingListAdapter(it.items)
                    recylerView?.layoutManager = LinearLayoutManager(context)
                    if(recylerView == null)
                        Log.d("trendng", "null")
                }
    }

    companion object {
        fun newInstance(): TrendingFragment {
            val fragment = TrendingFragment()
            return fragment
        }
    }

}