package com.github.af2905.movieland.presentation.feature.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentHomeBinding
import com.github.af2905.movieland.helper.text.ResourceUIText
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.common.pager.FragmentPagerAdapter
import com.github.af2905.movieland.presentation.common.pager.PageItem
import com.github.af2905.movieland.presentation.common.pager.setupPager
import com.github.af2905.movieland.presentation.feature.home.popular.PopularMovieFragment
import com.github.af2905.movieland.presentation.feature.home.top.TopRatedMovieFragment
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator

class HomeFragment : BaseFragment<HomeNavigator, FragmentHomeBinding, HomeViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private val nowPlayingAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(MovieItem.VIEW_TYPE,
            listener = MovieItem.Listener { item, position ->
                viewModel.openDetail(item.id, position)
            })
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = nowPlayingAdapter
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            addItemDecoration(
                HorizontalListItemDecorator(
                    marginStart = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginEnd = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = this.context.resources.getDimensionPixelSize(R.dimen.default_margin_small)
                )
            )
        }

        setupPager(
            tabLayout = binding.pagerTabs,
            viewPager = binding.pager,
            adapter = FragmentPagerAdapter(
                fragment = this,
                items = listOf(
                    PageItem(ResourceUIText(R.string.popular)) { PopularMovieFragment() },
                    PageItem(ResourceUIText(R.string.top_rated)) { TopRatedMovieFragment() },
                    PageItem(ResourceUIText(R.string.upcoming)) { UpcomingMovieFragment() },
                )
            )
        )

        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.setForceUpdate()
        }

        viewModel.updateFailed.observe(viewLifecycleOwner, { failed ->
            if (failed) binding.homeSwipeRefreshLayout.isRefreshing = false
        })
    }
}