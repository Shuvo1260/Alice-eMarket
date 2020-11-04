package org.binaryitplanet.aliceemarket.Features.Adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_news_item.view.*
import org.binaryitplanet.aliceemarket.R
import org.binaryitplanet.aliceemarket.Utils.NewsUtils

class NewsAdapter(
    val context: Context,
    val newsList: ArrayList<NewsUtils>
): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val TAG = "ProductAdapter"


    // Holding the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout.view_news_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = newsList.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var view = holder.itemView

        try {

            // Setting up the data into views of news items
            if (position == newsList.size - 1) {
                view.margin.visibility = View.VISIBLE
            } else {
                view.margin.visibility = View.GONE
            }

            view.title.text = newsList[position].title
            view.news.text = newsList[position].news

        } catch (e: Exception) {
            Log.d(TAG, "NewsListViewError: ${e.message}")
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {}
}