package com.github.af2905.movieland.home.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.AppBarStateChangeListener
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.HorizontalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.HomeMenuItem
import com.github.af2905.movieland.core.common.pager.FragmentPagerAdapter
import com.github.af2905.movieland.core.common.pager.PageItem
import com.github.af2905.movieland.core.common.pager.setupPager
import com.github.af2905.movieland.core.common.text.ResourceUiText
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.home.R
import com.github.af2905.movieland.home.databinding.FragmentHomeBinding
import com.github.af2905.movieland.home.di.component.DaggerHomeComponent
import com.github.af2905.movieland.home.di.component.HomeComponent
import com.github.af2905.movieland.home.presentation.movies.nowPlayingMovies.NowPlayingMovieFragment
import com.github.af2905.movieland.home.presentation.movies.popularMovies.PopularMovieFragment
import com.github.af2905.movieland.home.presentation.movies.topRatedMovies.TopRatedMovieFragment
import com.github.af2905.movieland.home.presentation.movies.upcomingMovies.UpcomingMovieFragment
import com.github.af2905.movieland.home.presentation.tvShows.popularTvShows.PopularTvShowsFragment
import com.github.af2905.movieland.home.presentation.tvShows.topRatedTvShows.TopRatedTvShowsFragment
import com.google.android.material.appbar.AppBarLayout

class HomeFragment : BaseFragment<HomeNavigator, FragmentHomeBinding, HomeViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    lateinit var homeComponent: HomeComponent

    private val baseAdapter: BaseAdapter =
        BaseAdapter(
            ItemDelegate(HomeMenuItem.VIEW_TYPE,
                listener = HomeMenuItem.Listener { item ->

                }
            ))

    private val appBarStateChangeListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
                    binding.toolbar.background = ColorDrawable(typedValue.data)
                }
                State.IDLE -> binding.toolbar.background = ColorDrawable(Color.TRANSPARENT)
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {
            //unused
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        homeComponent = DaggerHomeComponent.factory().create(appComponent)
        homeComponent.injectHomeFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            //val snapHelper = LinearSnapHelper()
            //snapHelper.attachToRecyclerView(this)
            addItemDecoration(
                HorizontalListItemDecorator(
                    marginStart = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginEnd = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = this.context.resources.getDimensionPixelSize(R.dimen.default_margin)
                )
            )
        }

        setupPager(
            tabLayout = binding.pagerTabs,
            viewPager = binding.pager,
            adapter = FragmentPagerAdapter(
                fragment = this,
                items = listOf(
                    PageItem(ResourceUiText(R.string.popular)) { PopularMovieFragment() },
                    PageItem(ResourceUiText(R.string.top_rated)) { TopRatedMovieFragment() },
                    PageItem(ResourceUiText(R.string.upcoming)) { UpcomingMovieFragment() },
                    PageItem(ResourceUiText(R.string.now_playing)) { NowPlayingMovieFragment() },
                    PageItem(ResourceUiText(R.string.popular)) { PopularTvShowsFragment() },
                    PageItem(ResourceUiText(R.string.top_rated)) { TopRatedTvShowsFragment() }
                )
            )
        )

        binding.innerAppBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            binding.homeSwipeRefreshLayout.isEnabled = verticalOffset >= 0
        }

        binding.innerAppBarLayout.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.setForceUpdate()
            finishRefresh()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is HomeContract.Effect.OpenMenuItemDetail -> handleEffect(effect.navigator)
                    is HomeContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }

    }

    private fun finishRefresh() {
        binding.homeSwipeRefreshLayout.isRefreshing = false
    }
}