package com.github.af2905.movieland.profile.presentation.optionLiked

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.base.navigator.AppNavigator
import com.github.af2905.movieland.core.common.pager.FragmentPagerAdapter
import com.github.af2905.movieland.core.common.pager.PageItem
import com.github.af2905.movieland.core.common.pager.setupPager
import com.github.af2905.movieland.core.common.text.ResourceUiText
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.profile.R
import com.github.af2905.movieland.profile.databinding.FragmentOptionLikedBinding
import com.github.af2905.movieland.profile.di.DaggerProfileComponent
import com.github.af2905.movieland.profile.presentation.optionLiked.movies.LikedMoviesFragment
import com.github.af2905.movieland.profile.presentation.optionLiked.people.LikedPeopleFragment

class OptionLikedFragment :
    BaseFragment<AppNavigator, FragmentOptionLikedBinding, OptionLikedViewModel>() {

    override fun getNavigator(navController: NavController) = AppNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_option_liked
    override fun viewModelClass(): Class<OptionLikedViewModel> = OptionLikedViewModel::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        profileComponent.injectOptionLikedFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        setupPager(
            tabLayout = binding.pagerTabs,
            viewPager = binding.pager,
            adapter = FragmentPagerAdapter(
                fragment = this,
                items = listOf(
                    PageItem(ResourceUiText(R.string.liked_movies_fragment_name)) { LikedMoviesFragment() },
                    PageItem(ResourceUiText(R.string.liked_people_fragment_name)) { LikedPeopleFragment() }

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
}