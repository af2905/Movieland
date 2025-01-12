package com.github.af2905.movieland.movies.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.AppBarStateChangeListener
import com.github.af2905.movieland.core.common.pager.FragmentPagerAdapter
import com.github.af2905.movieland.core.common.pager.PageItem
import com.github.af2905.movieland.core.common.pager.setupPager
import com.github.af2905.movieland.core.common.text.ResourceUiText
import com.github.af2905.movieland.movies.R
import com.github.af2905.movieland.movies.databinding.FragmentMoviesBinding
import com.github.af2905.movieland.movies.presentation.nowPlayingMovies.NowPlayingMovieFragment
import com.github.af2905.movieland.movies.presentation.popularMovies.PopularMovieFragment
import com.github.af2905.movieland.movies.presentation.topRatedMovies.TopRatedMovieFragment
import com.github.af2905.movieland.movies.presentation.upcomingMovies.UpcomingMovieFragment
import com.google.android.material.appbar.AppBarLayout

class MoviesFragment : BaseFragment<MoviesNavigator, FragmentMoviesBinding, MoviesViewModel>() {

    override fun getNavigator(navController: NavController) = MoviesNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_movies
    override fun viewModelClass(): Class<MoviesViewModel> = MoviesViewModel::class.java

    //lateinit var moviesComponent: MoviesComponent

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
       /* val appComponent = CoreComponentProvider.getAppComponent(context)
        moviesComponent = DaggerMoviesComponent.factory().create(appComponent)
        moviesComponent.injectMoviesFragment(this)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        binding.appBar.addOnOffsetChangedListener { _, verticalOffset ->
            binding.moviesSwipeRefreshLayout.isEnabled = verticalOffset >= 0
        }

        binding.appBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        binding.moviesSwipeRefreshLayout.setOnRefreshListener {
            viewModel.setForceUpdate()
            finishRefresh()
        }

        setupPager(
            tabLayout = binding.pagerTabs,
            viewPager = binding.pager,
            adapter = FragmentPagerAdapter(
                fragment = this,
                items = listOf(
                    PageItem(ResourceUiText(R.string.popular)) { PopularMovieFragment() },
                    PageItem(ResourceUiText(R.string.now_playing)) { NowPlayingMovieFragment() },
                    PageItem(ResourceUiText(R.string.top_rated)) { TopRatedMovieFragment() },
                    PageItem(ResourceUiText(R.string.upcoming)) { UpcomingMovieFragment() }
                )
            )
        )
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun finishRefresh() {
        binding.moviesSwipeRefreshLayout.isRefreshing = false
    }
}