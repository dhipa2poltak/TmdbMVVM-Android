package com.dpfht.tmdbmvvm.feature.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbmvvm.R
import com.dpfht.tmdbmvvm.databinding.FragmentGenreBinding
import com.dpfht.tmdbmvvm.feature.genre.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment: Fragment() {

  private lateinit var binding: FragmentGenreBinding
  private val viewModel by viewModels<GenreViewModel>()

  @Inject
  lateinit var adapter: GenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter.genres = viewModel.genres

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        val navDirections = viewModel.getNavDirectionsOnClickGenreAt(position)
        Navigation.findNavController(requireView()).navigate(navDirections)
      }
    }

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

    viewModel.start()
  }

  private fun showErrorMessage(message: String) {
    val navDirections = GenreFragmentDirections.actionGenreFragmentToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  private fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
