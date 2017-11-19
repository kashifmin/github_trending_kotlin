package me.kashifminhaj.githubtrending.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending.view.*
import me.kashifminhaj.githubtrending.R
import me.kashifminhaj.githubtrending.apis.Models

/**
 * Created by kashif on 18/11/17.
 */
class TrendingListAdapter(var data: List<Models.TrendingItem>): RecyclerView.Adapter<TrendingListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindTrendingItem(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_trending, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        fun bindTrendingItem(item: Models.TrendingItem) {
            with(item) {
                itemView.name.text = item.fullName
                itemView.stars.text = item.stars.toString()
                if(item.description != null){
                    itemView.description.text = item.description
                }
                Picasso.with(v.context).load(item.owner.avatarUrl).into(itemView.avatar)
            }
        }

    }
}