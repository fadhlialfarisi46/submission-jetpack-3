package com.example.submissionjetpack1.moviefavorited

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.vo.Resource


class MovieFavViewModel(private val movietvRepository: MovietvRepository): ViewModel() {

    fun getFavMovies(): LiveData<PagedList<MovieEntity>> = movietvRepository.getFavoritedMovies()

    fun setFavorite(movieEntity: MovieEntity){
        val newState = !movieEntity.favorited
        movietvRepository.setMovieFavorite(movieEntity, newState)
    }
}