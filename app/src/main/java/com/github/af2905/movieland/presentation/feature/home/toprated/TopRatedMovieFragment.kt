package com.github.af2905.movieland.presentation.feature.home.toprated

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.CoreComponentProvider
import com.github.af2905.movieland.R
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.item.MovieItemVariant
import com.github.af2905.movieland.databinding.FragmentTopRatedMovieBinding
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.di.component.DaggerTopRatedMovieComponent
import com.github.af2905.movieland.presentation.feature.home.di.component.HomeComponentProvider
import com.github.af2905.movieland.presentation.widget.VerticalListItemDecorator
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

class TopRatedMovieFragment :
    BaseFragment<HomeNavigator, FragmentTopRatedMovieBinding, TopRatedMovieViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_top_rated_movie
    override fun viewModelClass(): Class<TopRatedMovieViewModel> =
        TopRatedMovieViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item, _ -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = HomeComponentProvider.getHomeComponent(parentFragment)!!
        val topRatedMovieComponent =
            DaggerTopRatedMovieComponent.factory().create(appComponent, homeComponent)
        topRatedMovieComponent.injectTopRatedMovieFragment(this)
    }

    @OptIn(InternalCoroutinesApi::class)
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
            viewModel.container.effect.collect(FlowCollector { effect ->
                when (effect) {
                    is TopRatedMovieContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is TopRatedMovieContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            })
        }
    }
}