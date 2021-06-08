package com.example.submissionjetpack1.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.submissionjetpack1.data.source.local.LocalDataSource
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.remote.RemoteDataSource
import com.example.submissionjetpack1.utils.AppExecutors
import com.example.submissionjetpack1.utils.DataDummy
import com.example.submissionjetpack1.utils.LiveDataTestUtil
import com.example.submissionjetpack1.utils.PagedListUtil
import com.example.submissionjetpack1.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.doAnswer
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovietvRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movietvRepository = FakeMovietvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvshowResponses = DataDummy.generateRemoteDummyTv()
    private val tvshowId = tvshowResponses[0].id

    @Test
    fun getAllMovies(){
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSource)
        movietvRepository.getAllMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvshows(){
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvshows()).thenReturn(dataSource)
        movietvRepository.getAllTv()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTv()))
        verify(local).getAllTvshows()

        assertNotNull(tvshowEntities.data)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie(){
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getDetailMovie(movieId)).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(movietvRepository.getMovie(movieId))
        verify(local).getDetailMovie(movieId)
        assertNotNull(movieEntities)
        assertNotNull(movieEntities.title)
        assertEquals(movieResponses[0].title, movieEntities.title)
    }

    @Test
    fun getTvshow(){
        val dummyEntity = MutableLiveData<TvShowEntity>()
        dummyEntity.value = DataDummy.generateDummyTv()[0]
        `when`(local.getDetailTvshow(tvshowId)).thenReturn(dummyEntity)

        val tvshowEntities = LiveDataTestUtil.getValue(movietvRepository.getTvshow(tvshowId))
        verify(local).getDetailTvshow(tvshowId)
        assertNotNull(tvshowEntities)
        assertNotNull(tvshowEntities.title)
        assertEquals(tvshowResponses[0].title, tvshowEntities.title)
    }

    @Test
    fun getFavMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoritedMovie()).thenReturn(dataSourceFactory)
        movietvRepository.getFavoritedMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoritedMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavTvshow(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoritedTvshow()).thenReturn(dataSourceFactory)
        movietvRepository.getFavoritedTvshows()

        val tvshowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTv()))
        verify(local).getFavoritedTvshow()
        assertNotNull(tvshowEntities)
        assertEquals(tvshowResponses.size.toLong(), tvshowEntities.data?.size?.toLong())
    }

}