package com.example.submissionjetpack1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.detail.DetailTvshowViewModel
import com.example.submissionjetpack1.di.Injection
import com.example.submissionjetpack1.movie.MovieViewModel
import com.example.submissionjetpack1.moviefavorited.MovieFavViewModel
import com.example.submissionjetpack1.tvshow.TvshowViewModel
import com.example.submissionjetpack1.tvshowfavorited.TvshowFavViewModel

class ViewModelFactory private constructor(private val mMovietvRepository: MovietvRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }

            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mMovietvRepository) as T
            }
            modelClass.isAssignableFrom(TvshowViewModel::class.java) ->{
                return TvshowViewModel(mMovietvRepository) as T
            }
            modelClass.isAssignableFrom(TvshowFavViewModel::class.java) ->{
                return TvshowFavViewModel(mMovietvRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvshowViewModel::class.java) -> {
                return DetailTvshowViewModel(mMovietvRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavViewModel::class.java) -> {
                return MovieFavViewModel(mMovietvRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}