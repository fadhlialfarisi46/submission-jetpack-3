package com.example.submissionjetpack1.moviefavorited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionjetpack1.R
import com.example.submissionjetpack1.databinding.FragmentFavMovieBinding
import com.example.submissionjetpack1.databinding.FragmentMovieBinding
import com.example.submissionjetpack1.viewmodel.ViewModelFactory
import com.example.submissionjetpack1.vo.Status
import com.google.android.material.snackbar.Snackbar

class MovieFavFragment : Fragment()  {

    private lateinit var binding: FragmentFavMovieBinding
    private lateinit var movieAdapter: MovieFavAdapter
    private lateinit var viewModel: MovieFavViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieFavViewModel::class.java]

            movieAdapter = MovieFavAdapter()

            binding.progressBar.visibility = View.VISIBLE
            viewModel.getFavMovies().observe(viewLifecycleOwner, { movies ->
                binding.progressBar.visibility = View.GONE
                movieAdapter.submitList(movies)
            })

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
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
                val movieEntity = movieAdapter.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavorite(it) }

                val snackbar = Snackbar.make(view as View, getString(R.string.batal_hapus), Snackbar.LENGTH_LONG)
                snackbar.setAction(getString(R.string.ya)){ _ ->
                    movieEntity?.let {  viewModel.setFavorite(it) }
                }
                snackbar.show()
            }
        }

    })
}