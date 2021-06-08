package com.example.submissionjetpack1.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionjetpack1.data.source.remote.response.MovieResponse
import com.example.submissionjetpack1.data.source.remote.response.TvshowResponse
import com.example.submissionjetpack1.utils.EspressoIdlingResource
import com.example.submissionjetpack1.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {

        private const val SERVICE_LATENCY: Long = 1000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovie(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovie())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY)
        return resultMovie
    }

    fun getAllTv(): LiveData<ApiResponse<List<TvshowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvshow = MutableLiveData<ApiResponse<List<TvshowResponse>>>()
        handler.postDelayed({
            resultTvshow.value = ApiResponse.success(jsonHelper.loadTv())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY)
        return resultTvshow
    }

}