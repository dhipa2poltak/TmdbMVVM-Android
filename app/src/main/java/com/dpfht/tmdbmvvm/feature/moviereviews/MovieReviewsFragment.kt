package com.dpfht.tmdbmvvm.feature.moviereviews

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
import com.dpfht.tmdbmvvm.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbmvvm.feature.moviereviews.adapter.MovieReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewsFragment: Fragment() {

  private lateinit var binding: FragmentMovieReviewsBinding
  private val viewModel by viewModels<MovieReviewsViewModel>()

  @Inject
  lateinit var adapter: MovieReviewsAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieReviewsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.reviews = viewModel.reviews

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = adapter

    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          viewModel.getMovieReviews()
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

    //--

    val args = MovieReviewsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId
    val movieTitle = args.movieTitle

    binding.tvMovieName.text = movieTitle

    viewModel.setMovieId(movieId)
    viewModel.start()
  }

  private fun showErrorMessage(message: String) {
    val navDirections = MovieReviewsFragmentDirections.actionMovieReviewsToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
