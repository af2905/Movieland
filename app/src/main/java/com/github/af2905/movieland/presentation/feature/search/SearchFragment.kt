package com.github.af2905.movieland.presentation.feature.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentSearchBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import com.github.af2905.movieland.presentation.model.item.SearchItem
import com.github.af2905.movieland.presentation.widget.VerticalListItemDecorator
import kotlinx.coroutines.flow.collect

class SearchFragment : BaseFragment<SearchNavigator, FragmentSearchBinding, SearchViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_search
    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java
    override fun getNavigator(navController: NavController) = SearchNavigator(navController)

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item, _ ->
                viewModel.openDetail(item.id)
            }
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.listener = object : SearchItem.Listener {
            override fun textChanged(text: String) = viewModel.searchTextChanged(text)
            override fun deleteTextClicked() = viewModel.searchDeleteTextClicked()
        }
        binding.searchRecyclerView.apply {
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
            viewModel.container.state.collect { state ->
                when (state) {
                    is SearchContract.State.Loading -> Unit
                    is SearchContract.State.EmptyQuery -> viewModel.handleEmptyQuery(state.list)
                    is SearchContract.State.Success -> viewModel.handleSuccess(state.list)
                    is SearchContract.State.EmptyResult -> viewModel.handleEmptyResult()
                    is SearchContract.State.Error -> {
                        viewModel.showError(ErrorHandler.handleError(state.e))
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.container.effect.collect { effect ->
                when (effect) {
                    is SearchContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is SearchContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            }
        }
    }
}