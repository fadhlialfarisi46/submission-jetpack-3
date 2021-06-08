package com.example.submissionjetpack1.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.vo.Resource

interface MovietvDataSource {

    fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTv(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvshow(tvshowId: String): LiveData<TvShowEntity>

    fun getMovie(movieId: String): LiveData<MovieEntity>

    fun getFavoritedMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoritedTvshows(): LiveData<PagedList<TvShowEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTvshowFavorite(tvshow: TvShowEntity, state: Boolean)


}