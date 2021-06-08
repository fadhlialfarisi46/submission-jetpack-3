package com.example.submissionjetpack1.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.NetworkBoundResource
import com.example.submissionjetpack1.data.source.local.LocalDataSource
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.remote.ApiResponse
import com.example.submissionjetpack1.data.source.remote.RemoteDataSource
import com.example.submissionjetpack1.data.source.remote.response.MovieResponse
import com.example.submissionjetpack1.data.source.remote.response.TvshowResponse
import com.example.submissionjetpack1.utils.AppExecutors
import com.example.submissionjetpack1.vo.Resource

class FakeMovietvRepository constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource, private val appExecutors: AppExecutors) :
    MovietvDataSource {


    override fun getAllMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()

            }


            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()


            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movie = MovieEntity(
                        response.id,
                        response.type,
                        response.title,
                        response.description,
                        response.genre,
                        response.release,
                        response.runtime,
                        response.imagePath,
                        false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvshowResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvshows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvshowResponse>>> =
                remoteDataSource.getAllTv()

            override fun saveCallResult(data: List<TvshowResponse>) {
                val tvshowList = ArrayList<TvShowEntity>()
                for (response in data){
                    val tvshow = TvShowEntity(
                        response.id,
                        response.type,
                        response.title,
                        response.description,
                        response.genre,
                        response.release,
                        response.runtime,
                        response.imagePath,
                        false
                    )
                    tvshowList.add(tvshow)
                }
                localDataSource.insertTvshows(tvshowList)
            }
        }.asLiveData()
    }

    override fun getTvshow(tvshowId: String): LiveData<TvShowEntity> =
        localDataSource.getDetailTvshow(tvshowId)

    override fun getMovie(movieId: String): LiveData<MovieEntity> =
        localDataSource.getDetailMovie(movieId)

    override fun getFavoritedMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedMovie(), config).build()
    }

    override fun getFavoritedTvshows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedTvshow(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }

    override fun setTvshowFavorite(tvshow: TvShowEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setTvshowFavorite(tvshow, state) }

}