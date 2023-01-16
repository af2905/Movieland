package com.github.af2905.movieland.tvshows.presentation

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
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.tvshows.R
import com.github.af2905.movieland.tvshows.databinding.FragmentTvShowsBinding
import com.github.af2905.movieland.tvshows.di.component.DaggerTvShowsComponent
import com.github.af2905.movieland.tvshows.di.component.TvShowsComponent
import com.github.af2905.movieland.tvshows.presentation.popularTvShows.PopularTvShowsFragment
import com.github.af2905.movieland.tvshows.presentation.topRatedTvShows.TopRatedTvShowsFragment
import com.google.android.material.appbar.AppBarLayout

class TvShowsFragment : BaseFragment<TvShowsNavigator, FragmentTvShowsBinding, TvShowsViewModel>() {

    override fun getNavigator(navController: NavController) = TvShowsNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_tv_shows
    override fun viewModelClass(): Class<TvShowsViewModel> = TvShowsViewModel::class.java

    lateinit var tvShowsComponent: TvShowsComponent

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
        tvShowsComponent = DaggerTvShowsComponent.factory().create(appComponent)
        tvShowsComponent.injectTvShowsFragment(this)
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
                    PageItem(ResourceUiText(R.string.popular)) { PopularTvShowsFragment() },
                    PageItem(ResourceUiText(R.string.top_rated)) { TopRatedTvShowsFragment() },
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