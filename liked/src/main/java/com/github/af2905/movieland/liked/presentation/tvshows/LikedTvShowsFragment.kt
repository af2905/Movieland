package com.github.af2905.movieland.liked.presentation.tvshows

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.IntentFilterKey
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.TvShowV2Item
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.databinding.FragmentLikedTvShowsBinding
import com.github.af2905.movieland.liked.di.DaggerLikedComponent

class LikedTvShowsFragment :
    BaseFragment<LikedTvShowsNavigator, FragmentLikedTvShowsBinding, LikedTvShowsViewModel>() {
    override fun getNavigator(navController: NavController) = LikedTvShowsNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_liked_tv_shows
    override fun viewModelClass(): Class<LikedTvShowsViewModel> = LikedTvShowsViewModel::class.java

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            viewType = TvShowV2Item.VIEW_TYPE,
            listener = TvShowV2Item.Listener { item -> viewModel.openDetail(item.id) }
        ))

    private val likedTvShowsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.loadData()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val likedComponent = DaggerLikedComponent.factory().create(appComponent)
        likedComponent.injectLikedTvShowsFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context?.registerReceiver(
                likedTvShowsBroadcastReceiver,
                IntentFilter(IntentFilterKey.LIKED_TV_SHOW),
                Context.RECEIVER_EXPORTED
            )
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context?.registerReceiver(
                likedTvShowsBroadcastReceiver,
                IntentFilter(IntentFilterKey.LIKED_TV_SHOW)
            )
        }

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
                    is LikedTvShowsContract.Effect.OpenTvShowDetail -> handleEffect(effect.navigator)
                }
            }
        }
    }
}