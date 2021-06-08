package com.example.submissionjetpack1.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.vo.Resource


class MovieViewModel(private val movietvRepository: MovietvRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movietvRepository.getAllMovie()
}