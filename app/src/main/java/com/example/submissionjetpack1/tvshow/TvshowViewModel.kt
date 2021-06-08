package com.example.submissionjetpack1.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.data.source.MovietvRepository
import com.example.submissionjetpack1.vo.Resource

class TvshowViewModel(private val movietvRepository: MovietvRepository): ViewModel() {

    fun getTvshows(): LiveData<Resource<PagedList<TvShowEntity>>> = movietvRepository.getAllTv()
}