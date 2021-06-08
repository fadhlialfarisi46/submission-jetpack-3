package com.example.submissionjetpack1.di

import android.content.Context
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.data.source.local.LocalDataSource
import com.example.submissionjetpack1.data.source.local.room.MovieTvshowDatabase
import com.example.submissionjetpack1.data.source.remote.RemoteDataSource
import com.example.submissionjetpack1.utils.AppExecutors
import com.example.submissionjetpack1.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MovietvRepository{

        val database = MovieTvshowDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()
        return MovietvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}