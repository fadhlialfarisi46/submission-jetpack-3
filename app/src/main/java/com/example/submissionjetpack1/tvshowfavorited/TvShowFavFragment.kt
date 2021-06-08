package com.example.submissionjetpack1.tvshowfavorited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionjetpack1.R
import com.example.submissionjetpack1.databinding.FragmentFavTvShowBinding
import com.example.submissionjetpack1.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class TvShowFavFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentFavTvShowBinding
    private lateinit var tvshowAdapter: TvshowFavAdapter
    private lateinit var viewModel: TvshowFavViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentFavTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(fragmentTvShowBinding.rvTvshow)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvshowFavViewModel::class.java]

            tvshowAdapter = TvshowFavAdapter()

            fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavTvshows().observe(viewLifecycleOwner, { tvshows ->
                fragmentTvShowBinding.progressBar.visibility = View.GONE
                tvshowAdapter.submitList(tvshows)
            })
            with(fragmentTvShowBinding.rvTvshow){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvshowAdapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null){
                val swipedPosition = viewHolder.adapterPosition
                val tvShowEntity = tvshowAdapter.getSwipedData(swipedPosition)
                tvShowEntity?.let { viewModel.setFavorite(it) }

                val snackbar = Snackbar.make(view as View, getString(R.string.batal_hapus), Snackbar.LENGTH_LONG)
                snackbar.setAction(getString(R.string.ya)){ _ ->
                    tvShowEntity?.let {  viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }

    })

}