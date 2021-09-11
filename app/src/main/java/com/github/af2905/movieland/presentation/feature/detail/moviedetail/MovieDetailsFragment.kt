package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentMovieDetailsBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.feature.detail.DetailNavigator
import com.github.af2905.movieland.presentation.model.item.HorizontalListAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator

class MovieDetailsFragment :
    BaseFragment<DetailNavigator, FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_movie_details
    override fun viewModelClass(): Class<MovieDetailsViewModel> = MovieDetailsViewModel::class.java
    override fun getNavigator(navController: NavController) = DetailNavigator(navController)

    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieDetailsSwipeRefreshLayout.isEnabled = false

        binding.movieDetailsRecyclerView.apply {
            adapter = ListAdapter(
                HorizontalListAdapter(
                    layout = HorizontalListItem.VIEW_TYPE,
                    adapter = {
                        ListAdapter(
                            ItemAdapter(
                                MovieActorItem.VIEW_TYPE,
                                listener = MovieActorItem.Listener { item, position ->
                                    viewModel.openActorDetail(item, position)
                                })
                        )
                    },
                    decoration = {
                        HorizontalListItemDecorator(
                            marginStart = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            marginEnd = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            spacing = it.resources.getDimensionPixelSize(R.dimen.default_margin_small)
                        )
                    }
                ),
                HorizontalListAdapter(
                    layout = HorizontalListItem.VIEW_TYPE,
                    adapter = {
                        ListAdapter(
                            ItemAdapter(
                                MovieItem.VIEW_TYPE,
                                listener = MovieItem.Listener { item, position ->
                                    viewModel.openSimilarMovieDetail(item, position)
                                })
                        )
                    },
                    decoration = {
                        HorizontalListItemDecorator(
                            marginStart = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            marginEnd = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            spacing = it.resources.getDimensionPixelSize(R.dimen.default_margin_small)
                        )
                    }
                )
            )
        }
    }
}