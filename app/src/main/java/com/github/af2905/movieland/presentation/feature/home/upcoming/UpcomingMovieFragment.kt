package com.github.af2905.movieland.presentation.feature.home.upcoming

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentUpcomingMovieBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import com.github.af2905.movieland.presentation.widget.VerticalListItemDecorator
import kotlinx.coroutines.flow.collect

class UpcomingMovieFragment :
    BaseFragment<HomeNavigator, FragmentUpcomingMovieBinding, UpcomingMovieViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_upcoming_movie
    override fun viewModelClass(): Class<UpcomingMovieViewModel> =
        UpcomingMovieViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item, _ -> viewModel.openDetail(item.id) })
    )

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