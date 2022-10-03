package com.example.celebritiesapp

import android.content.ContentValues
import android.support.annotation.Keep
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.Headers
import org.json.JSONException

private const val SEARCH_API_KEY = BuildConfig.API_KEY


@Keep
@Serializable
data class SearchCelebrityResults(
    @SerialName("results")
    val result: List<Celebrity>?
)

@Keep
@Serializable
data class Celebrity(
    @SerialName("name")
    var cel_name:String?,
    @SerialName("id")
    val cel_id:Int?,
    @SerialName("popularity")
    val cel_popularity:Float?,
    @SerialName("profile_path")
    val cel_image:String?
): java.io.Serializable{
    val cel_imageURL = "https://image.tmdb.org/t/p/w500/${cel_image}"
}



