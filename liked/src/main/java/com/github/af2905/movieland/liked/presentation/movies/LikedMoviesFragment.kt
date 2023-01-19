package com.github.af2905.movieland.liked.presentation.movies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.IntentFilterKey
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.MovieV2Item
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.databinding.FragmentLikedMoviesBinding
import com.github.af2905.movieland.liked.di.DaggerLikedComponent

class LikedMoviesFragment :
    BaseFragment<LikedMoviesNavigator, FragmentLikedMoviesBinding, LikedMoviesViewModel>() {
    override fun getNavigator(navController: NavController) = LikedMoviesNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked_movies
    override fun viewModelClass(): Class<LikedMoviesViewModel> = LikedMoviesViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            viewType = MovieV2Item.VIEW_TYPE,
            listener = MovieV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    private val likedMoviesBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.loadData()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val likedComponent = DaggerLikedComponent.factory().create(appComponent)
        likedComponent.injectLikedMoviesFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.registerReceiver(
            likedMoviesBroadcastReceiver,
            IntentFilter(IntentFilterKey.LIKED_MOVIE)
        )

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
                    is LikedMoviesContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}