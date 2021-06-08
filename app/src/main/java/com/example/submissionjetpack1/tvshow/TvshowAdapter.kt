package com.example.submissionjetpack1.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionjetpack1.R
import com.example.submissionjetpack1.data.source.local.entity.MovieEntity
import com.example.submissionjetpack1.data.source.local.entity.TvShowEntity
import com.example.submissionjetpack1.databinding.ItemsMovietvshowBinding
import com.example.submissionjetpack1.detail.DetailTvshowActivity

class TvshowAdapter: PagedListAdapter<TvShowEntity, TvshowAdapter.TvshowViewHolder>(DIFF_CALLBACK) {
//
//    private var listTVshow = ArrayList<TvShowEntity>()
//
//    fun setTvshow(tvshows: List<TvShowEntity>?){
//        if (tvshows == null) return
//        this.listTVshow.clear()
//        this.listTVshow.addAll(tvshows)
//    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>(){
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    class TvshowViewHolder(private val binding: ItemsMovietvshowBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(tvshow: TvShowEntity){
                with(binding){
                    tvItemTitle.text = tvshow.title
                    tvItemRelease.text = tvshow.release
                    tvItemGenre.text = tvshow.genre
                    Glide.with(itemView.context)
                        .load(tvshow.imagePath)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                        .into(imgPoster)
                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailTvshowActivity::class.java)
                        intent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshow.id)
                        intent.putExtra(DetailTvshowActivity.EXTRA_TYPE, tvshow.type)
                        itemView.context.startActivity(intent)
                    }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvshowViewHolder {
        val itemsMovietvshowBinding = ItemsMovietvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvshowViewHolder(itemsMovietvshowBinding)
    }

    override fun onBindViewHolder(holder: TvshowViewHolder, position: Int) {
        val tvshow = getItem(position)
        if (tvshow != null) {
            holder.bind(tvshow)
        }
    }
}