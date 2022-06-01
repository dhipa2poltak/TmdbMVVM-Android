package com.dpfht.tmdbmvvm.feature.moviesbygenre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvvm.databinding.RowMovieBinding
import com.dpfht.tmdbmvvm.data.model.Movie
import javax.inject.Inject

class MoviesByGenreAdapter @Inject constructor(

): RecyclerView.Adapter<MoviesByGenreAdapter.MovieByGenreHolder>() {

  lateinit var movies: ArrayList<Movie>
  var onClickMovieListener: OnClickMovieListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreHolder {
    val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return MovieByGenreHolder(binding)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: MovieByGenreHolder, position: Int) {
    holder.bindData(movies[position])
    holder.itemView.setOnClickListener {
      onClickMovieListener?.onClickMovie(position)
    }
  }

  class MovieByGenreHolder(private val binding: RowMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(movie: Movie) {
      binding.tvTitleMovie.text = movie.title
    }
  }

  interface OnClickMovieListener {
    fun onClickMovie(position: Int)
  }
}
