package com.github.af2905.movieland.detail.moviedetail.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.*
import com.github.af2905.movieland.core.common.helper.ThemeHelper
import com.github.af2905.movieland.core.common.model.decorator.HorizontalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.CreditsCastItem
import com.github.af2905.movieland.core.common.model.item.HorizontalListAdapter
import com.github.af2905.movieland.core.common.model.item.HorizontalListItem
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.databinding.FragmentMovieDetailBinding
import com.github.af2905.movieland.detail.moviedetail.MovieDetailNavigator
import com.google.android.material.appbar.AppBarLayout

class MovieDetailFragment :
    BaseFragment<MovieDetailNavigator, FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_movie_detail
    override fun viewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java
    override fun getNavigator(navController: NavController) = MovieDetailNavigator(navController)

    private val baseAdapter: BaseAdapter = NestedRecyclerViewStateAdapter(
        HorizontalListAdapter(
            layout = HorizontalListItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        MovieItem.VIEW_TYPE,
                        listener = MovieItem.Listener { item ->
                            viewModel.navigateToMovieDetail(item.id)
                        }),
                    ItemDelegate(
                        CreditsCastItem.VIEW_TYPE,
                        listener = CreditsCastItem.Listener { item ->
                            viewModel.navigateToPersonDetail(item.id)
                        })
                )
            },
            decoration = { getHorizontalListItemDecoration(it) }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
/*        val appComponent = CoreComponentProvider.getAppComponent(context)
        val detailComponent = DaggerMovieDetailComponent.factory().create(
            coreComponent = appComponent,
            movieId = requireNotNull(arguments?.getInt(MOVIE_ID_ARG))
        )
        detailComponent.injectMovieDetailFragment(this)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply { adapter = baseAdapter }

        initToolbar()

        val appBarStateChangeListener = getAppBarStateChangeListener()

        binding.movieDetailToolbar.toolbar.navigationIcon?.setTint(Color.WHITE)
        binding.movieDetailToolbar.movieDetailCollapsingToolbarLayout
            .setExpandedTitleColor(Color.WHITE)

        binding.movieDetailToolbar.movieDetailAppBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is MovieDetailContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.OpenPreviousScreen -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                    is MovieDetailContract.Effect.LikeClicked -> {
                        context?.sendBroadcast(Intent(IntentFilterKey.LIKED_MOVIE))
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.movieDetailToolbar.toolbar)
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
                    binding.movieDetailToolbar.toolbar.apply {
                        if (ThemeHelper.isCurrentThemeDark(context)) {
                            navigationIcon?.setTint(Color.WHITE)
                        } else {
                            navigationIcon?.setTint(Color.DKGRAY)
                        }
                    }
                }
                State.IDLE -> {
                    binding.movieDetailToolbar.toolbar.apply {
                        background = ColorDrawable(Color.TRANSPARENT)
                        navigationIcon?.setTint(Color.WHITE)
                    }
                }
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {
            //unused
        }
    }

    private fun getHorizontalListItemDecoration(context: Context): HorizontalListItemDecorator {
        return HorizontalListItemDecorator(
            marginStart = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            marginEnd = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            spacing = context.resources.getDimensionPixelSize(R.dimen.default_margin)
        )
    }

    companion object {
        const val MOVIE_ID_ARG =
            "com.github.af2905.movieland.detail.moviedetail.presentation.MOVIE_ID_ARG"
    }
}