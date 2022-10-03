package com.example.celebritiesapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val CELEBRITY_EXTRA = "CELEBRITY_EXTRA"
private const val SEARCH_API_KEY = BuildConfig.API_KEY

class CelebrityAdapter(private val context:Context, private val celebrities: List<Celebrity>):
    RecyclerView.Adapter<CelebrityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.celebrity_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the individual celebrity and bind to holder
        val celebrity = celebrities[position]
        holder.bind(celebrity)
    }

    override fun getItemCount() = celebrities.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val celebrityImageView = itemView.findViewById<ImageView>(R.id.celebrityImage)
        private val nameTextView = itemView.findViewById<TextView>(R.id.celebrityName)
        private val popularityTextView = itemView.findViewById<TextView>(R.id.celebrityPopularity)

        init {
            itemView.setOnClickListener(this)
        }

        //set up the onBindViewHolder method
        fun bind(celebrity: Celebrity) {
            nameTextView.text = celebrity.cel_name
            popularityTextView.text = "Popularity Score: ${celebrity.cel_popularity.toString()}"

            Glide.with(context)
                .load(celebrity.cel_imageURL)
                .into(celebrityImageView)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val celebrity = celebrities[adapterPosition]


            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(CELEBRITY_EXTRA, celebrity)
            context.startActivity(intent)
        }


    }
}