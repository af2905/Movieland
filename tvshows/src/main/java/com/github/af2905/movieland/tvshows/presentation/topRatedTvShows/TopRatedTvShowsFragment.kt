package com.github.af2905.movieland.tvshows.presentation.topRatedTvShows

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.TvShowV2Item
import com.github.af2905.movieland.tvshows.R
import com.github.af2905.movieland.tvshows.databinding.FragmentTopRatedTvShowsBinding
import com.github.af2905.movieland.tvshows.presentation.TvShowsNavigator

class TopRatedTvShowsFragment :
    BaseFragment<TvShowsNavigator, FragmentTopRatedTvShowsBinding, TopRatedTvShowsViewModel>() {

    override fun getNavigator(navController: NavController) = TvShowsNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_top_rated_tv_shows
    override fun viewModelClass(): Class<TopRatedTvShowsViewModel> =
        TopRatedTvShowsViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            viewType = TvShowV2Item.VIEW_TYPE,
            listener = TvShowV2Item.Listener { item -> viewModel.openDetail(item.id) }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
/*        val appComponent = CoreComponentProvider.getAppComponent(context)
        val tvShowsComponent = TvShowsComponentProvider.getTvShowsComponent(parentFragment)!!
        val topRatedTvShowsComponent =
            DaggerTopRatedTvShowsComponent.factory().create(appComponent, tvShowsComponent)
        topRatedTvShowsComponent.injectTopRatedTvShowsFragment(this)*/
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
                when (effect) {
                    is TopRatedTvShowsContract.Effect.OpenTvShowDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}
