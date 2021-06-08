package com.example.submissionjetpack1.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailTvshowViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movietvRepository: MovietvRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieEntity>

    @Before
    fun setUp(){
        viewModel = DetailTvshowViewModel(movietvRepository)
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun testGetTvshow() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie

        `when`(movietvRepository.getMovie(movieId)).thenReturn(movie)
        val movieEntity = movietvRepository.getMovie(movieId).value
        verify(movietvRepository).getMovie(movieId)
        TestCase.assertNotNull(movieEntity)
        TestCase.assertEquals(dummyMovie.id, movieEntity?.id)
        TestCase.assertEquals(dummyMovie.type, movieEntity?.type)
        TestCase.assertEquals(dummyMovie.title, movieEntity?.title)
        TestCase.assertEquals(dummyMovie.description, movieEntity?.description)
        TestCase.assertEquals(dummyMovie.genre, movieEntity?.genre)
        TestCase.assertEquals(dummyMovie.release, movieEntity?.release)
        TestCase.assertEquals(dummyMovie.runtime, movieEntity?.runtime)
        TestCase.assertEquals(dummyMovie.imagePath, movieEntity?.imagePath)

        viewModel.getMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }
}