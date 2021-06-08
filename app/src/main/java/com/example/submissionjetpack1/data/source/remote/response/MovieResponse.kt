package com.example.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    var id: String,
    var type: String,
    var title: String,
    var description: String,
    var genre: String,
    var release: String,
    var runtime:String,
    var imagePath: String
):Parcelable
