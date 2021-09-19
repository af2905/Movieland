package com.github.af2905.movieland.presentation.feature.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentHomeBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator

class HomeFragment : BaseFragment<HomeNavigator, FragmentHomeBinding, HomeViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nowPlayingMoviesRecyclerView.apply {
            setMovieItemAdapterWithDecoration(requireContext(), this)
        }
        binding.popularMoviesRecyclerView.apply {
            setMovieItemAdapterWithDecoration(requireContext(), this)
        }
        binding.topRatedMoviesRecyclerView.apply {
            setMovieItemAdapterWithDecoration(requireContext(), this)
        }
        binding.upcomingMoviesRecyclerView.apply {
            setMovieItemAdapterWithDecoration(requireContext(), this)
        }
        binding.homeSwipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    private fun setMovieItemAdapterWithDecoration(context: Context, recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = ListAdapter(
                ItemAdapter(MovieItem.VIEW_TYPE,
                    listener = MovieItem.Listener { item, position ->
                        viewModel.openDetail(item, position)
                    })
            )
            addItemDecoration(
                HorizontalListItemDecorator(
                    marginStart = context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginEnd = context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = context.resources.getDimensionPixelSize(R.dimen.default_margin_small)
                )
            )
        }
    }
}