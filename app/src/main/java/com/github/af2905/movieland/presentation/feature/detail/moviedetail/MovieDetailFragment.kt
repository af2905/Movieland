package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.github.af2905.movieland.CoreComponentProvider
import com.github.af2905.movieland.R
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.base.RetainStoreFragment
import com.github.af2905.movieland.core.common.AppBarStateChangeListener
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.NestedRecyclerViewStateAdapter
import com.github.af2905.movieland.core.common.model.item.HorizontalListAdapter
import com.github.af2905.movieland.core.common.model.item.HorizontalListItem
import com.github.af2905.movieland.core.common.model.item.MovieActorItem
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.databinding.FragmentMovieDetailBinding
import com.github.af2905.movieland.helper.ThemeHelper
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.di.DaggerMovieDetailComponent
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.di.MovieDetailComponent
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

private const val COMPONENT_TAG = "component"

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
                        MovieItem.VIEW_TYPE,
                        listener = MovieItem.Listener { item, _ ->
                            viewModel.openSimilarMovieDetail(item.id)
                        }),
                    ItemDelegate(
                        MovieActorItem.VIEW_TYPE,
                        listener = MovieActorItem.Listener { item, _ ->
                            viewModel.openPersonDetail(item.id)
                        })
                )
            },
            decoration = { getHorizontalListItemDecoration(it) }
        )
    )

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment =
            childFragmentManager.findFragmentByTag(COMPONENT_TAG) as? RetainStoreFragment<MovieDetailComponent>

        val component = fragment?.component

        if (component != null) {
            component.injectMovieDetailFragment(this)
        } else {
            val appComponent = CoreComponentProvider.getAppComponent(context)
            val detailComponent = DaggerMovieDetailComponent.factory().create(appComponent, args)
            detailComponent.injectMovieDetailFragment(this)

            val retainStoreFragment = RetainStoreFragment<MovieDetailComponent>()
            retainStoreFragment.component = detailComponent
            childFragmentManager.commit {
                add(retainStoreFragment, COMPONENT_TAG)
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
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

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect(FlowCollector { effect ->
                when (effect) {
                    is MovieDetailContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                    is MovieDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            })
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

        override fun onScrolled(state: State, dy: Int) {
            Unit
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