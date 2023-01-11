package com.github.af2905.movieland.movies.presentation.nowPlayingMovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.MovieV2Item
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.movies.R
import com.github.af2905.movieland.movies.databinding.FragmentNowPlayingMovieBinding
import com.github.af2905.movieland.movies.di.component.DaggerNowPlayingMovieComponent
import com.github.af2905.movieland.movies.di.component.MoviesComponentProvider
import com.github.af2905.movieland.movies.presentation.MoviesNavigator

class NowPlayingMovieFragment :
    BaseFragment<MoviesNavigator, FragmentNowPlayingMovieBinding, NowPlayingMovieViewModel>() {

    override fun getNavigator(navController: NavController) = MoviesNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_now_playing_movie
    override fun viewModelClass(): Class<NowPlayingMovieViewModel> =
        NowPlayingMovieViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieV2Item.VIEW_TYPE,
            listener = MovieV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val moviesComponent = MoviesComponentProvider.getMoviesComponent(parentFragment)!!
        val nowPlayingMovieComponent =
            DaggerNowPlayingMovieComponent.factory().create(appComponent, moviesComponent)
        nowPlayingMovieComponent.injectNowPlayingMovieFragment(this)
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
                    is NowPlayingMovieContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is NowPlayingMovieContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }
}