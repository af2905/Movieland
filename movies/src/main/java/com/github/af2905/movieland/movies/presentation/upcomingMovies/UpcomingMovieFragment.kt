package com.github.af2905.movieland.movies.presentation.upcomingMovies

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
import com.github.af2905.movieland.movies.R
import com.github.af2905.movieland.movies.databinding.FragmentUpcomingMovieBinding
import com.github.af2905.movieland.movies.presentation.MoviesNavigator

class UpcomingMovieFragment :
    BaseFragment<MoviesNavigator, FragmentUpcomingMovieBinding, UpcomingMovieViewModel>() {

    override fun getNavigator(navController: NavController) = MoviesNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_upcoming_movie
    override fun viewModelClass(): Class<UpcomingMovieViewModel> =
        UpcomingMovieViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieV2Item.VIEW_TYPE,
            listener = MovieV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
/*        val appComponent = CoreComponentProvider.getAppComponent(context)
        val moviesComponent = MoviesComponentProvider.getMoviesComponent(parentFragment)!!
        val upcomingMovieComponent =
            DaggerUpcomingMovieComponent.factory().create(appComponent, moviesComponent)
        upcomingMovieComponent.injectUpcomingMovieFragment(this)*/
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
                    is UpcomingMovieContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is UpcomingMovieContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }
}