package com.github.af2905.movieland.presentation.feature.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentHomeBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListAdapter
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.widget.HorizontalListItemDecorator

class HomeFragment : BaseFragment<HomeNavigator, FragmentHomeBinding, HomeViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_home
    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecyclerView.apply {
            adapter = ListAdapter(
                HorizontalListAdapter(
                    layout = HorizontalListItem.VIEW_TYPE,
                    adapter = {
                        ListAdapter(
                            ItemAdapter(MovieItem.VIEW_TYPE,
                                listener = MovieItem.Listener { item, position ->
                                    viewModel.openDetail(item, position)
                                })
                        )
                    },
                    decoration = {
                        HorizontalListItemDecorator(
                            marginStart = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            marginEnd = it.resources.getDimensionPixelSize(R.dimen.default_margin),
                            spacing = it.resources.getDimensionPixelSize(R.dimen.default_margin_small)
                        )
                    }
                )
            )
        }
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}