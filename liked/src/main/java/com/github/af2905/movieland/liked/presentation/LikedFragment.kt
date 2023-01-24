package com.github.af2905.movieland.liked.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.base.navigator.AppNavigator
import com.github.af2905.movieland.core.common.pager.FragmentPagerAdapter
import com.github.af2905.movieland.core.common.pager.PageItem
import com.github.af2905.movieland.core.common.pager.setupPager
import com.github.af2905.movieland.core.common.text.ResourceUiText
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.databinding.FragmentLikedBinding
import com.github.af2905.movieland.liked.di.DaggerLikedComponent
import com.github.af2905.movieland.liked.presentation.movies.LikedMoviesFragment
import com.github.af2905.movieland.liked.presentation.people.LikedPeopleFragment
import com.github.af2905.movieland.liked.presentation.tvshows.LikedTvShowsFragment

class LikedFragment : BaseFragment<AppNavigator, FragmentLikedBinding, LikedViewModel>() {

    override fun getNavigator(navController: NavController) = AppNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked
    override fun viewModelClass(): Class<LikedViewModel> = LikedViewModel::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val likedComponent = DaggerLikedComponent.factory().create(appComponent)
        likedComponent.injectLikedFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPager(
            tabLayout = binding.pagerTabs,
            viewPager = binding.pager,
            adapter = FragmentPagerAdapter(
                fragment = this,
                items = listOf(
                    PageItem(ResourceUiText(R.string.liked_movies_fragment_name)) { LikedMoviesFragment() },
                    PageItem(ResourceUiText(R.string.liked_tv_shows_fragment_name)) { LikedTvShowsFragment() },
                    PageItem(ResourceUiText(R.string.liked_people_fragment_name)) { LikedPeopleFragment() }
                )
            )
        )
    }
}