package com.github.af2905.movieland.presentation.feature.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.core.base.BaseFragment
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.decorator.VerticalListItemDecorator
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieItemVariant
import com.github.af2905.movieland.core.common.model.item.SearchItem
import com.github.af2905.movieland.core.common.model.item.SearchQueryItem
import com.github.af2905.movieland.core.di.CoreComponentProvider
import com.github.af2905.movieland.databinding.FragmentSearchBinding
import com.github.af2905.movieland.presentation.feature.search.di.DaggerSearchComponent

class SearchFragment : BaseFragment<SearchNavigator, FragmentSearchBinding, SearchViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_search
    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java
    override fun getNavigator(navController: NavController) = SearchNavigator(navController)

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item, _ -> viewModel.openDetail(item.id) }
        ),
        ItemDelegate(
            SearchQueryItem.VIEW_TYPE,
            listener = SearchQueryItem.Listener { item -> viewModel.searchTextChanged(item.title) }
        ),
        ItemDelegate(
            ErrorItem.VIEW_TYPE,
            listener = ErrorItem.Listener { viewModel.update() }
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = CoreComponentProvider.getAppComponent(context)
        val searchComponent = DaggerSearchComponent.factory().create(appComponent)
        searchComponent.injectSearchFragment(this)
    }

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
            viewModel.container.effect.collect({ effect ->
                when (effect) {
                    is SearchContract.Effect.OpenMovieDetail -> handleEffect(effect.navigator)
                    is SearchContract.Effect.ShowFailMessage -> handleEffect(effect.message)
                }
            })
        }
    }
}