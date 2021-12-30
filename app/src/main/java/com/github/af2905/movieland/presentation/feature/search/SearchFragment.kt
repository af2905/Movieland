package com.github.af2905.movieland.presentation.feature.search

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentSearchBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import com.github.af2905.movieland.presentation.model.item.SearchItem

class SearchFragment : BaseFragment<SearchNavigator, FragmentSearchBinding, SearchViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_search
    override fun viewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java
    override fun getNavigator(navController: NavController) = SearchNavigator(navController)

    private val baseAdapter: BaseAdapter = BaseAdapter(
        ItemDelegate(
            MovieItemVariant.VIEW_TYPE,
            listener = MovieItemVariant.Listener { item, position ->
                viewModel.openDetail(item.id, position)
            }
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.listener = object : SearchItem.Listener {
            override fun textChanged(text: String) = viewModel.searchTextChanged(text)
            override fun deleteTextClicked() = viewModel.searchDeleteTextClicked()
        }
        binding.searchRecyclerView.apply { adapter = baseAdapter }

        viewModel.searchResult.observe(viewLifecycleOwner, viewModel::handleMoviesList)
    }
}