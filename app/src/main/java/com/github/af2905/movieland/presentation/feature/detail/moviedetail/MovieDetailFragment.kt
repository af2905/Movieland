package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentMovieDetailBinding
import com.github.af2905.movieland.helper.ThemeHelper
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.AppBarStateChangeListener
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.common.NestedRecyclerViewStateAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.flow.collect

class MovieDetailFragment :
    BaseFragment<MovieDetailNavigator, FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_movie_detail
    override fun viewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java
    override fun getNavigator(navController: NavController) = MovieDetailNavigator(navController)

    val args: MovieDetailFragmentArgs by navArgs()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply { adapter = baseAdapter }

        initToolbar()

        val appBarStateChangeListener = getAppBarStateChangeListener()

        binding.movieDetailsToolbar.toolbar.navigationIcon?.setTint(Color.WHITE)
        binding.movieDetailsToolbar.movieDetailCollapsingToolbarLayout
            .setExpandedTitleColor(Color.WHITE)

        binding.movieDetailsToolbar.movieDetailsAppBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

/*        lifecycleScope.launchWhenCreated {
            viewModel.container.state.collect { state ->
                when (state) {
                    is MovieDetailContract.State.Error -> {
                        viewModel.showError(ErrorHandler.handleError(state.e))
                    }
                    else -> Unit
                }
            }
        }*/

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is MovieDetailContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.OpenActorDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.movieDetailsToolbar.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun getAppBarStateChangeListener() = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    binding.movieDetailsToolbar.toolbar.apply {
                        if (ThemeHelper.isCurrentThemeDark(context)) {
                            navigationIcon?.setTint(Color.WHITE)
                        } else {
                            navigationIcon?.setTint(Color.DKGRAY)
                        }
                    }
                }
                State.IDLE -> {
                    binding.movieDetailsToolbar.toolbar.apply {
                        background = ColorDrawable(Color.TRANSPARENT)
                        navigationIcon?.setTint(Color.WHITE)
                    }
                }
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {}
    }

    private fun getHorizontalListItemDecoration(context: Context): HorizontalListItemDecorator {
        return HorizontalListItemDecorator(
            marginStart = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            marginEnd = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            spacing = context.resources.getDimensionPixelSize(R.dimen.default_margin)
        )
    }
}