package com.example.submissionjetpack1.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.local.room.MovieTvDao

class LocalDataSource private constructor(private val mMovieTvshowDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieTvshowDao.getMovies()

    fun getAllTvshows(): DataSource.Factory<Int, TvShowEntity> = mMovieTvshowDao.getTvshows()

    fun getFavoritedMovie(): DataSource.Factory<Int, MovieEntity> = mMovieTvshowDao.getFavoritedMovie()

    fun getFavoritedTvshow(): DataSource.Factory<Int, TvShowEntity> = mMovieTvshowDao.getFavoritedTvshow()

    fun getDetailMovie(id: String): LiveData<MovieEntity> = mMovieTvshowDao.getDetailMovie(id)

    fun getDetailTvshow(id: String): LiveData<TvShowEntity> = mMovieTvshowDao.getDetailTvshow(id)

    fun insertMovies(movies: List<MovieEntity>) = mMovieTvshowDao.insertMovies(movies)

    fun insertTvshows(tvshows: List<TvShowEntity>) = mMovieTvshowDao.insertTvshows(tvshows)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mMovieTvshowDao.updateMovies(movie)
    }

    fun setTvshowFavorite(tvshow: TvShowEntity, newState: Boolean) {
        tvshow.favorited = newState
        mMovieTvshowDao.updateTvshow(tvshow)
    }

}
