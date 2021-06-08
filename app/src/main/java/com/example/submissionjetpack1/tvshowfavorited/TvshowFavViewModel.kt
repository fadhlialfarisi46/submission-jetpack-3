package com.example.submissionjetpack1.tvshowfavorited

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity

class TvshowFavViewModel(private val movietvRepository: MovietvRepository): ViewModel() {

    fun getFavTvshows(): LiveData<PagedList<TvShowEntity>> = movietvRepository.getFavoritedTvshows()

    fun setFavorite(tvShowEntity: TvShowEntity){
        val newState = !tvShowEntity.favorited
        movietvRepository.setTvshowFavorite(tvShowEntity, newState)
    }
}