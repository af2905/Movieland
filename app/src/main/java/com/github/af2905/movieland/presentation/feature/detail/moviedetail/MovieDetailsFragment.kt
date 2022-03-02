package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentMovieDetailsBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.AppBarStateChangeListener
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.common.NestedRecyclerViewStateAdapter
import com.github.af2905.movieland.presentation.feature.detail.DetailNavigator
import com.github.af2905.movieland.presentation.model.item.HorizontalListAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.flow.collect

class MovieDetailsFragment :
    BaseFragment<DetailNavigator, FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_movie_details
    override fun viewModelClass(): Class<MovieDetailsViewModel> = MovieDetailsViewModel::class.java
    override fun getNavigator(navController: NavController) = DetailNavigator(navController)

    val args: MovieDetailsFragmentArgs by navArgs()

    private val baseAdapter: BaseAdapter = NestedRecyclerViewStateAdapter(
        HorizontalListAdapter(
            layout = HorizontalListItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        MovieActorItem.VIEW_TYPE,
                        listener = MovieActorItem.Listener { item, position ->
                            //viewModel.openActorDetail(item, position)
                        })
                )
            },
            decoration = { getHorizontalListItemDecoration(it) }
        ),
        HorizontalListAdapter(
            layout = HorizontalListItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        MovieItem.VIEW_TYPE,
                        listener = MovieItem.Listener { item, _ ->
                            viewModel.openSimilarMovieDetail(item.id)
                        })
                )
            },
            decoration = { getHorizontalListItemDecoration(it) }
        )
    )

    private val appBarStateChangeListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
                    binding.movieDetailsToolbar.toolbar.background = ColorDrawable(typedValue.data)
                }
                State.IDLE -> binding.movieDetailsToolbar.toolbar.background =
                    ColorDrawable(Color.TRANSPARENT)
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieDetailsSwipeRefreshLayout.isEnabled = false
        binding.recyclerView.apply { adapter = baseAdapter }

        lifecycleScope.launchWhenCreated {
            viewModel.container.state.collect { state ->
                when (state) {
                    is MovieDetailContract.State.Loading -> {}
                    is MovieDetailContract.State.Content -> {
                        viewModel.updateSuccessData(
                            movieDetails = state.movieDetailsItem,
                            items = state.list
                        )
                    }
                    is MovieDetailContract.State.EmptyResult -> {
                        /* viewModel.updateData(emptyList(), false)
                         finishRefresh()*/
                    }
                    is MovieDetailContract.State.Error -> {
                        /* viewModel.showError(ErrorHandler.handleError(state.e))
                         finishRefresh()*/
                    }
                }
            }
        }

        binding.movieDetailsToolbar.movieDetailsAppBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is MovieDetailContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.OpenActorDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.MoveToBackScreen -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }

    private fun getHorizontalListItemDecoration(context: Context): HorizontalListItemDecorator {
        return HorizontalListItemDecorator(
            marginStart = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            marginEnd = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            spacing = context.resources.getDimensionPixelSize(R.dimen.default_margin)
        )
    }
}