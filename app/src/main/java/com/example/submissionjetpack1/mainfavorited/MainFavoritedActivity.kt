package com.example.submissionjetpack1.mainfavorited

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submissionjetpack1.databinding.ActivityMainFavoritedBinding

class MainFavoritedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityFavoriteBinding = ActivityMainFavoritedBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionPagerFavAdapter = SectionPagerFavAdapter(this, supportFragmentManager)
        activityFavoriteBinding.viewPager.adapter = sectionPagerFavAdapter
        activityFavoriteBinding.tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

    }
}