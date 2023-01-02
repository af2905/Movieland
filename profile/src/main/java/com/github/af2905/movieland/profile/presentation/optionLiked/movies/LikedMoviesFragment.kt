package com.github.af2905.movieland.profile.presentation.optionLiked.movies

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
import com.github.af2905.movieland.profile.R
import com.github.af2905.movieland.profile.databinding.FragmentLikedMoviesBinding
import com.github.af2905.movieland.profile.di.DaggerProfileComponent

class LikedMoviesFragment :
    BaseFragment<LikedMoviesNavigator, FragmentLikedMoviesBinding, LikedMoviesViewModel>() {
    override fun getNavigator(navController: NavController) = LikedMoviesNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked_movies
    override fun viewModelClass(): Class<LikedMoviesViewModel> = LikedMoviesViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieV2Item.VIEW_TYPE,
            listener = MovieV2Item.Listener { item -> viewModel.openDetail(item.id) })
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        profileComponent.injectLikedMoviesFragment(this)
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
                    is LikedMoviesContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}