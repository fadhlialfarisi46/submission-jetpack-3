package com.example.submissionjetpack1.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.MovietvRepository

class DetailTvshowViewModel(private val movietvRepository: MovietvRepository) : ViewModel() {

    private val tvshowId = MutableLiveData<String>()

    fun setSelectedTvshow(tvshowId: String) {
        this.tvshowId.value = tvshowId
    }

    fun getTvshow(): LiveData<TvShowEntity> = Transformations.switchMap(tvshowId) { mTvshowId ->
        movietvRepository.getTvshow(mTvshowId)
    }

    fun setFavoriteTvshow(tvshow: TvShowEntity, state: Boolean){
        val newState = !state
        movietvRepository.setTvshowFavorite(tvshow, newState)
    }

    private val movieId = MutableLiveData<String>()

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    fun getMovie(): LiveData<MovieEntity> = Transformations.switchMap(movieId) { mMovieId ->
        movietvRepository.getMovie(mMovieId)
    }


    fun setFavoriteMovie(movie: MovieEntity, state: Boolean){
         val newState = !state
        movietvRepository.setMovieFavorite(movie, newState)
    }
}