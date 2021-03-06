package me.kashifminhaj.githubtrending.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending.view.*
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.apis.Models

/**
 * Created by kashif on 18/11/17.
 */
class TrendingListAdapter(var data: List<Models.TrendingItem>?, val toggleListener: OnFavoriteToggleListener): RecyclerView.Adapter<TrendingListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindTrendingItem(data!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_trending, parent, false)
        return ViewHolder(view, toggleListener)
    }

    override fun getItemCount(): Int {
        if (data != null)
            return data!!.size
        else
            return 0
    }


    class ViewHolder(val v: View, val toggleListener: OnFavoriteToggleListener): RecyclerView.ViewHolder(v) {
        fun bindTrendingItem(item: Models.TrendingItem) {
            with(item) {
                itemView.name.text = item.fullName
                itemView.stars.text = item.stars.toString()
                itemView.favoriteButton.setOnClickListener { toggleListener.onToggle(item, itemView.favoriteButton) }
                if(item.description != null){
                    itemView.description.text = item.description
                } else {
                    itemView.description.text = "No description."
                }
                if(item.isFavorite)
                    itemView.favoriteButton.setImageDrawable(v.context.getDrawable(R.drawable.ic_favorite))
                else
                    itemView.favoriteButton.setImageDrawable(v.context.getDrawable(R.drawable.ic_favorite_border))
                Picasso.with(v.context).load(item.owner.avatarUrl).into(itemView.avatar)
            }
        }


    }

    interface OnFavoriteToggleListener {
        fun onToggle(item: Models.TrendingItem, btn: ImageView)
    }
}