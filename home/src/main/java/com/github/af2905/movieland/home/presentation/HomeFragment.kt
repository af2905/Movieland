package com.github.af2905.movieland.home.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.AppBarStateChangeListener
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.NestedRecyclerViewStateAdapter
import com.github.af2905.movieland.core.common.model.decorator.HorizontalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.home.R
import com.github.af2905.movieland.home.databinding.FragmentHomeBinding
import com.github.af2905.movieland.home.di.component.DaggerHomeComponent
import com.github.af2905.movieland.home.presentation.item.PagerMovieItem
import com.github.af2905.movieland.home.presentation.item.PopularPersonItem
import com.google.android.material.appbar.AppBarLayout

class HomeFragment : BaseFragment<HomeNavigator, FragmentHomeBinding, HomeViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private val baseAdapter: BaseAdapter = NestedRecyclerViewStateAdapter(
        HorizontalListAdapter(
            layout = HorizontalListItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        viewType = MovieItem.VIEW_TYPE,
                        listener = MovieItem.Listener { item ->
                            viewModel.openMovieDetail(item.id)
                        }
                    ),
                    ItemDelegate(
                        viewType = PopularPersonItem.VIEW_TYPE,
                        listener = PopularPersonItem.Listener { item ->
                            viewModel.openPersonDetail(item.id)
                        }
                    )
                )
            },
            decoration = { getHorizontalListItemDecoration(it) },
        ),
        PagerAdapter(
            layout = PagerItem.VIEW_TYPE,
            adapter = {
                BaseAdapter(
                    ItemDelegate(
                        viewType = PagerMovieItem.VIEW_TYPE,
                        listener = PagerMovieItem.Listener { item ->
                            viewModel.openMovieDetail(item.id)
                        }
                    )
                )
            }
        ),
        ItemDelegate(
            viewType = HeaderLinkItem.VIEW_TYPE,
            listener = HeaderLinkItem.Listener { item -> viewModel.openMore(item.type) }
        ),
        ItemDelegate(
            viewType = ErrorItem.VIEW_TYPE,
            listener = ErrorItem.Listener { viewModel.refresh() }
        )
    )

    private val appBarStateChangeListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when (state) {
                State.COLLAPSED -> {
                    val typedValue = TypedValue()
                    requireActivity().theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
                    binding.toolbar.background = ColorDrawable(typedValue.data)
                }
                State.IDLE -> binding.toolbar.background = ColorDrawable(Color.TRANSPARENT)
                else -> Unit
            }
        }

        override fun onScrolled(state: State, dy: Int) {
            //unused
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val homeComponent = DaggerHomeComponent.factory().create(appComponent)
        homeComponent.injectHomeFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply { adapter = baseAdapter }

        binding.innerAppBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            binding.homeSwipeRefreshLayout.isEnabled = verticalOffset >= 0
        }

        binding.innerAppBarLayout.apply {
            removeOnOffsetChangedListener(appBarStateChangeListener)
            addOnOffsetChangedListener(appBarStateChangeListener)
        }

        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is HomeContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is HomeContract.Effect.OpenPersonDetail -> handleEffect(effect.navigator)
                    is HomeContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                    is HomeContract.Effect.OpenMovies -> handleEffect(effect.navigator)
                    is HomeContract.Effect.OpenPeople -> handleEffect(effect.navigator)
                    is HomeContract.Effect.OpenTvShows -> handleEffect(effect.navigator)
                    is HomeContract.Effect.FinishRefresh -> finishRefresh()
                }
            }
        }
    }

    private fun finishRefresh() {
        binding.homeSwipeRefreshLayout.isRefreshing = false
    }

    private fun getHorizontalListItemDecoration(context: Context): HorizontalListItemDecorator {
        return HorizontalListItemDecorator(
            marginStart = context.resources.getDimensionPixelSize(com.github.af2905.movieland.detail.R.dimen.default_margin),
            marginEnd = context.resources.getDimensionPixelSize(com.github.af2905.movieland.detail.R.dimen.default_margin),
            spacing = context.resources.getDimensionPixelSize(com.github.af2905.movieland.detail.R.dimen.default_margin)
        )
    }
}