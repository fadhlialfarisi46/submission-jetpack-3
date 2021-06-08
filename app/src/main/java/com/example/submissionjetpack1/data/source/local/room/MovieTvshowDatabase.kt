package com.example.submissionjetpack1.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieTvshowDatabase: RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object{

        @Volatile
        private var INSTANCE: MovieTvshowDatabase? = null

        fun getInstance(context: Context): MovieTvshowDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvshowDatabase::class.java,
                    "MovieTvshow.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }

}