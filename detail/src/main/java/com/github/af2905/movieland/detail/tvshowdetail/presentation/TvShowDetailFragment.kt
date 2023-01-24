package com.github.af2905.movieland.detail.tvshowdetail.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.*
import com.github.af2905.movieland.core.common.helper.ThemeHelper
import com.github.af2905.movieland.core.common.model.decorator.HorizontalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.CreditsCastItem
import com.github.af2905.movieland.core.common.model.item.HorizontalListAdapter
import com.github.af2905.movieland.core.common.model.item.HorizontalListItem
import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.databinding.FragmentTvShowDetailBinding
import com.github.af2905.movieland.detail.tvshowdetail.TvShowDetailNavigator
import com.github.af2905.movieland.detail.tvshowdetail.di.DaggerTvShowDetailComponent
import com.google.android.material.appbar.AppBarLayout

class TvShowDetailFragment :
    BaseFragment<TvShowDetailNavigator, FragmentTvShowDetailBinding, TvShowDetailViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_tv_show_detail
    override fun viewModelClass(): Class<TvShowDetailViewModel> = TvShowDetailViewModel::class.java
    override fun getNavigator(navController: NavController) = TvShowDetailNavigator(navController)

    private val baseAdapter: BaseAdapter = NestedRecyclerViewStateAdapter(
        HorizontalListAdapter(
            layout = HorizontalListItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        TvShowItem.VIEW_TYPE,
                        listener = TvShowItem.Listener { item ->
                            viewModel.navigateToTvShowDetail(item.id)
                        }),
                    ItemDelegate(
                        CreditsCastItem.VIEW_TYPE,
                        listener = CreditsCastItem.Listener { item ->
                            viewModel.navigateToPersonDetail(item.id)
                        })
                )
            },
            decoration = { getHorizontalListItemDecoration(it) }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val detailComponent = DaggerTvShowDetailComponent.factory().create(
            coreComponent = appComponent,
            tvShowId = requireNotNull(arguments?.getInt(TV_SHOW_ID_ARG))
        )
        detailComponent.injectTvShowDetailFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply { adapter = baseAdapter }

        initToolbar()

        val appBarStateChangeListener = getAppBarStateChangeListener()

        binding.tvShowDetailToolbar.toolbar.navigationIcon?.setTint(Color.WHITE)
        binding.tvShowDetailToolbar.tvShowDetailCollapsingToolbarLayout
            .setExpandedTitleColor(Color.WHITE)

        binding.tvShowDetailToolbar.tvShowDetailAppBar.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is TvShowDetailContract.Effect.OpenTvShowDetail -> handleEffect(effect.navigator)
                    is TvShowDetailContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                    is TvShowDetailContract.Effect.OpenPreviousScreen -> handleEffect(effect.navigator)
                    is TvShowDetailContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                    is TvShowDetailContract.Effect.LikeClicked -> {
                        context?.sendBroadcast(Intent(IntentFilterKey.LIKED_TV_SHOW))
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.tvShowDetailToolbar.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun getAppBarStateChangeListener() = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    binding.tvShowDetailToolbar.toolbar.apply {
                        if (ThemeHelper.isCurrentThemeDark(context)) {
                            navigationIcon?.setTint(Color.WHITE)
                        } else {
                            navigationIcon?.setTint(Color.DKGRAY)
                        }
                    }
                }
                State.IDLE -> {
                    binding.tvShowDetailToolbar.toolbar.apply {
                        background = ColorDrawable(Color.TRANSPARENT)
                        navigationIcon?.setTint(Color.WHITE)
                    }
                }
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {
            //unused
        }
    }

    private fun getHorizontalListItemDecoration(context: Context): HorizontalListItemDecorator {
        return HorizontalListItemDecorator(
            marginStart = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            marginEnd = context.resources.getDimensionPixelSize(R.dimen.default_margin),
            spacing = context.resources.getDimensionPixelSize(R.dimen.default_margin)
        )
    }

    companion object {
        const val TV_SHOW_ID_ARG =
            "com.github.af2905.movieland.detail.tvshowdetail.presentation.TV_SHOW_ID_ARG"
    }
}