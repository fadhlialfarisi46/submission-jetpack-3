package com.example.submissionjetpack1.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentities")
    fun getTvshows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM movieentities where favorited = 1")
    fun getFavoritedMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentities where favorited = 1")
    fun getFavoritedTvshow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvshows(tvshows: List<TvShowEntity>)

    @Update
    fun updateMovies(movie: MovieEntity)

    @Update
    fun updateTvshow(tvshow: TvShowEntity)

    @Query("SELECT * FROM movieentities WHERE id = :id")
    fun getDetailMovie(id: String): LiveData<MovieEntity>

    @Query("SELECT * FROM  tvshowentities WHERE id = :id")
    fun getDetailTvshow(id: String): LiveData<TvShowEntity>
}