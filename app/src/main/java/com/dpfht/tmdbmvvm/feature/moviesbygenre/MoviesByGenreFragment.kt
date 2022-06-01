package com.dpfht.tmdbmvvm.feature.moviesbygenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvvm.R
import com.dpfht.tmdbmvvm.databinding.FragmentMoviesByGenreBinding
import com.dpfht.tmdbmvvm.feature.moviesbygenre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbmvvm.feature.moviesbygenre.adapter.MoviesByGenreAdapter.OnClickMovieListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesByGenreFragment: Fragment() {

  private lateinit var binding: FragmentMoviesByGenreBinding
  private val viewModel by viewModels<MoviesByGenreViewModel>()

  @Inject
  lateinit var adapter: MoviesByGenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.movies = viewModel.movies

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvMoviesByGenre.layoutManager = layoutManager
    binding.rvMoviesByGenre.adapter = adapter

    adapter.onClickMovieListener = object : OnClickMovieListener {
      override fun onClickMovie(position: Int) {
        val navDirections = viewModel.getNavDirectionsOnClickMovieAt(position)
        Navigation.findNavController(requireView()).navigate(navDirections)
      }
    }

    binding.rvMoviesByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          viewModel.getMoviesByGenre()
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    //--

    viewModel.isShowDialogLoading.observe(requireActivity()) { value ->
      if (value) {
        loadingDialog.show()
      } else {
        loadingDialog.dismiss()
      }
    }

    viewModel.notifyItemInserted.observe(requireActivity()) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
      }
    }

    viewModel.errorMessage.observe(requireActivity()) { message ->
      if (message.isNotEmpty()) {
        showErrorMessage(message)
      }
    }

    viewModel.showCanceledMessage.observe(requireActivity()) { isShow ->
      if (isShow) {
        showCanceledMessage()
      }
    }

    val args = MoviesByGenreFragmentArgs.fromBundle(requireArguments())
    val genreId = args.genreId
    val genreName = args.genreName

    val title = "$genreName movies"
    binding.tvTitle.text = title

    viewModel.setGenreId(genreId)
    viewModel.start()
  }

  private fun showErrorMessage(message: String) {
    val navDirections = MoviesByGenreFragmentDirections.actionMovieByGenreToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
