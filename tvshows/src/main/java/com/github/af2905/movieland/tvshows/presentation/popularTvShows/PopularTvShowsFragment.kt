package com.github.af2905.movieland.tvshows.presentation.popularTvShows

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.tvshows.R
import com.github.af2905.movieland.tvshows.databinding.FragmentPopularTvShowsBinding
import com.github.af2905.movieland.tvshows.di.component.DaggerPopularTvShowsComponent
import com.github.af2905.movieland.tvshows.di.component.TvShowsComponentProvider
import com.github.af2905.movieland.tvshows.presentation.TvShowsNavigator

class PopularTvShowsFragment :
    BaseFragment<TvShowsNavigator, FragmentPopularTvShowsBinding, PopularTvShowsViewModel>() {

    override fun getNavigator(navController: NavController) = TvShowsNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_popular_tv_shows
    override fun viewModelClass(): Class<PopularTvShowsViewModel> =
        PopularTvShowsViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val tvShowsComponent = TvShowsComponentProvider.getTvShowsComponent(parentFragment)!!
        val popularTvShowsComponent =
            DaggerPopularTvShowsComponent.factory().create(appComponent, tvShowsComponent)
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
