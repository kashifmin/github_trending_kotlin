package me.kashifminhaj.githubtrending.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.adapters.TrendingListAdapter
import me.kashifminhaj.githubtrending.apis.Models
import me.kashifminhaj.githubtrending.db.database
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment(), TrendingListAdapter.OnFavoriteToggleListener {


    var recylerView: RecyclerView? = null
    var adapter: TrendingListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recylerView = view?.findViewById<RecyclerView>(R.id.favoritesRecylerView)
        recylerView?.layoutManager = LinearLayoutManager(context)
        val instance = this
        doAsync {
            val data = context.database.getFavorites()!!
            uiThread {
                adapter = TrendingListAdapter(data, instance)
                recylerView?.adapter = adapter
            }

        }

    }

    override fun onToggle(item: Models.TrendingItem, btn: ImageView) {
        doAsync {
            context.database.removeFavorite(item)
            item.isFavorite = false
            uiThread { adapter?.data = adapter?.data?.filter { it.isFavorite } ; adapter?.notifyDataSetChanged() }

        }
    }



    companion object {
        fun newInstance(): FavoritesFragment {
            val fragment = FavoritesFragment()
            return fragment
        }
    }

}// Required empty public constructor
