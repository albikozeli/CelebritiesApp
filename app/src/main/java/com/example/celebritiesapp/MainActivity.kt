package com.example.celebritiesapp

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celebritiesapp.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val POPULAR_SEARCH_URL =
    "https://api.themoviedb.org/3/person/popular?api_key=${SEARCH_API_KEY}"


class MainActivity : AppCompatActivity() {
    private val celebrities = mutableListOf<Celebrity>()
    private lateinit var celebritiesRV: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        celebritiesRV = findViewById<RecyclerView>(R.id.celebritiesRV)

        //Set up Celebrity Adapter with celebrities
        val celebrityAdapter = CelebrityAdapter(this, celebrities)
        celebritiesRV.adapter = celebrityAdapter

        celebritiesRV.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            celebritiesRV.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get(POPULAR_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchCelebrityResults.serializer(),
                        json?.jsonObject.toString()
                    )


                    parsedJson.result?.let { list ->
                        celebrities.addAll(list)

                        // Reload the screen
                        celebrityAdapter.notifyDataSetChanged()
                    }


                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })

    }
}