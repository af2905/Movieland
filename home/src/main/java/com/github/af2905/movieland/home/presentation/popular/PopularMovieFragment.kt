package com.github.af2905.movieland.home.presentation.popular

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.MovieItemVariant
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.home.R
import com.github.af2905.movieland.home.databinding.FragmentPopularMovieBinding
import com.github.af2905.movieland.home.di.component.DaggerPopularMovieComponent
import com.github.af2905.movieland.home.di.component.HomeComponentProvider
import com.github.af2905.movieland.home.presentation.HomeNavigator

class PopularMovieFragment :
    BaseFragment<HomeNavigator, FragmentPopularMovieBinding, PopularMovieViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_popular_movie
    override fun viewModelClass(): Class<PopularMovieViewModel> = PopularMovieViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = HomeComponentProvider.getHomeComponent(parentFragment)!!
        val popularMovieComponent =
            DaggerPopularMovieComponent.factory().create(appComponent, homeComponent)
        popularMovieComponent.injectPopularMovieFragment(this)
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
                    is PopularMovieContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is PopularMovieContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }
}