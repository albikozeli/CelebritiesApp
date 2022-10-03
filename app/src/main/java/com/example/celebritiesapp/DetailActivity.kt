package com.example.celebritiesapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject

private const val SEARCH_API_KEY = BuildConfig.API_KEY

class DetailActivity: AppCompatActivity() {
    private lateinit var celebrityImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var birth_placeTextView: TextView
    private lateinit var professionTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.celebrity_detail)

        //Find views for the screen
        celebrityImageView = findViewById(R.id.celebrityImage)
        nameTextView = findViewById(R.id.celebrityName)
        birth_placeTextView = findViewById(R.id.celebrityBirthPlace)
        bioTextView = findViewById(R.id.celebrityBiography)
        professionTextView = findViewById(R.id.celebrityProfession)

        //get the extra from the intent
        val celebrity = intent.getSerializableExtra(CELEBRITY_EXTRA) as Celebrity

        val CELEBRITY_SEARCH_URL =
            "https://api.themoviedb.org/3/person/${celebrity.cel_id}?api_key=${SEARCH_API_KEY}"
        val client = AsyncHttpClient()
        client.get(CELEBRITY_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(ContentValues.TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i(ContentValues.TAG, "Successfully fetched celebrity details: $json")
                try {
                    if (json != null) {
                        val bio_info = json.jsonObject.get("biography")
                        bioTextView.text = bio_info.toString()

                        val birth_place = json.jsonObject.get("place_of_birth")
                        birth_placeTextView.text = birth_place.toString()

                        val profession = json.jsonObject.get("known_for_department")
                        professionTextView.text = "Profession: " + profession.toString()

                    }

                } catch (e: JSONException) {
                    Log.e(ContentValues.TAG, "Exception: $e")
                }
            }
        })



        //Set the celebrity details
        nameTextView.text = celebrity.cel_name

        //Load the image
        Glide.with(this)
            .load(celebrity.cel_imageURL)
            .into(celebrityImageView)

    }



}