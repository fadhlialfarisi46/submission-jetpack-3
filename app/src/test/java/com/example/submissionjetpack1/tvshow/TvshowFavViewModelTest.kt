package com.example.submissionjetpack1.tvshowfav

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.tvshowfavorited.TvshowFavViewModel
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TvshowFavViewModelTest{

    private lateinit var viewModel: TvshowFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movietvRepository: MovietvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp(){
        viewModel = TvshowFavViewModel(movietvRepository)
    }

    @Test
    fun getTvshows(){
        val dummyTvshows = pagedList
        `when`(dummyTvshows.size).thenReturn(5)
        val tvshows = MutableLiveData<PagedList<TvShowEntity>>()
        tvshows.value = dummyTvshows

        `when`(movietvRepository.getFavoritedTvshows()).thenReturn(tvshows)
        val tvShowEntities = viewModel.getFavTvshows().value
        verify<MovietvRepository>(movietvRepository).getFavoritedTvshows()
        assertNotNull(tvShowEntities)
        assertEquals(5, tvShowEntities?.size)

        viewModel.getFavTvshows().observeForever(observer)
        verify(observer).onChanged(dummyTvshows)
    }

}