package com.example.submissionjetpack1.utils

import android.content.Context
import com.example.submissionjetpack1.data.source.remote.response.MovieResponse
import com.example.submissionjetpack1.data.source.remote.response.TvshowResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException){
            e.printStackTrace()
            null
        }
    }

    fun loadMovie(): List<MovieResponse>{
        val list = ArrayList<MovieResponse>()
        try {
            val responseObj = JSONObject(parsingFileToString("MovieTvshowResponses.JSON").toString())
            val listArray = responseObj.getJSONArray("movie")
            for (i in 0 until listArray.length()){
                val movietv = listArray.getJSONObject(i)

                val id = movietv.getString("id")
                val type = movietv.getString("type")
                val title = movietv.getString("title")
                val description = movietv.getString("description")
                val genre = movietv.getString("genre")
                val release = movietv.getString("release")
                val runtime = movietv.getString("runtime")
                val imagePath = movietv.getString("imagePath")

                val movieResponse = MovieResponse(id, type, title, description, genre, release, runtime, imagePath)
                list.add(movieResponse)
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }

    fun loadTv(): List<TvshowResponse>{
        val list = ArrayList<TvshowResponse>()
        try {
            val responseObj = JSONObject(parsingFileToString("MovieTvshowResponses.JSON").toString())
            val listArray = responseObj.getJSONArray("tvshow")
            for (i in 0 until listArray.length()){
                val movietv = listArray.getJSONObject(i)

                val id = movietv.getString("id")
                val type = movietv.getString("type")
                val title = movietv.getString("title")
                val description = movietv.getString("description")
                val genre = movietv.getString("genre")
                val release = movietv.getString("release")
                val runtime = movietv.getString("runtime")
                val imagePath = movietv.getString("imagePath")

                val tvshowResponse = TvshowResponse(id, type, title, description, genre, release, runtime, imagePath)
                list.add(tvshowResponse)
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }

}

