package com.github.af2905.movieland.home.presentation.tvShows.popularTvShows

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.home.R
import com.github.af2905.movieland.home.databinding.FragmentPopularTvShowsBinding
import com.github.af2905.movieland.home.di.component.DaggerPopularTvShowsComponent
import com.github.af2905.movieland.home.di.component.HomeComponentProvider
import com.github.af2905.movieland.home.presentation.HomeNavigator

class PopularTvShowsFragment :
    BaseFragment<HomeNavigator, FragmentPopularTvShowsBinding, PopularTvShowsViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_popular_tv_shows
    override fun viewModelClass(): Class<PopularTvShowsViewModel> =
        PopularTvShowsViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = HomeComponentProvider.getHomeComponent(parentFragment)!!
        val popularTvShowsComponent =
            DaggerPopularTvShowsComponent.factory().create(appComponent, homeComponent)
        popularTvShowsComponent.injectPopularTvShowsFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = baseAdapter
            addItemDecoration(
                VerticalListItemDecorator(
                    marginTop = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    marginBottom = this.context.resources.getDimensionPixelSize(R.dimen.default_margin),
                    spacing = this.context.resources.getDimensionPixelSize(R.dimen.default_margin)
                )
            )
        }
        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->

            }
        }
    }
}