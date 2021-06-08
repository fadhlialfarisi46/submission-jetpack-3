package com.example.submissionjetpack1.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.utils.DataDummy
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
class DetailTvshowViewModelTest {

    private lateinit var viewModel: DetailTvshowViewModel
    private val dummyTvshow = DataDummy.generateDummyTv()[0]
    private val tvshowId = dummyTvshow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movietvRepository: MovietvRepository

    @Mock
    private lateinit var tvshowObserver: Observer<TvShowEntity>

    @Before
    fun setUp(){
        viewModel = DetailTvshowViewModel(movietvRepository)
        viewModel.setSelectedTvshow(tvshowId)
    }

    @Test
    fun testGetTvshow() {

        val tvshow = MutableLiveData<TvShowEntity>()
        tvshow.value = dummyTvshow

        `when`(movietvRepository.getTvshow(tvshowId)).thenReturn(tvshow)
        val tvShowEntity = movietvRepository.getTvshow(tvshowId).value
        verify(movietvRepository).getTvshow(tvshowId)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvshow.id, tvShowEntity?.id)
        assertEquals(dummyTvshow.type, tvShowEntity?.type)
        assertEquals(dummyTvshow.title, tvShowEntity?.title)
        assertEquals(dummyTvshow.description, tvShowEntity?.description)
        assertEquals(dummyTvshow.genre, tvShowEntity?.genre)
        assertEquals(dummyTvshow.release, tvShowEntity?.release)
        assertEquals(dummyTvshow.runtime, tvShowEntity?.runtime)
        assertEquals(dummyTvshow.imagePath, tvShowEntity?.imagePath)

        viewModel.getTvshow().observeForever(tvshowObserver)
        verify(tvshowObserver).onChanged(dummyTvshow)
    }
}