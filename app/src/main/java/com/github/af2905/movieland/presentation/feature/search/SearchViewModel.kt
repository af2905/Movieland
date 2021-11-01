package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<SearchNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private fun starter() = listOf<Model>(SearchItem())

    init {
        launchUI { _items.value = starter() }
    }

    fun onNewQuery(query: String) = loadData(query = query)

    fun openDetail(itemId: Int, position: Int) = navigate { forwardMovieDetail(itemId) }

    private fun loadData(init: Boolean = false, query: String = "") {
        launchUI {
            if (init) {
                _items.value = starter()
            } else {
                val movies =
                    getSearchMovie(SearchMovieParams(query = query)).extractData?.movies
                        ?.filterNot { it.backdropPath == null }

                val list = mutableListOf<Model>()

                if (movies.isNullOrEmpty()) {
                    list.apply {
                        add(SearchItem())
                        add(emptySpaceMedium)
                        add(SimpleTextItem(R.string.search_empty_result))
                    }
                } else {
                    list.apply {
                        add(SearchItem())
                        add(emptySpaceMedium)
                        movies.map {
                            add(MovieItemVariant(it))
                            add(DividerItem())
                        }
                    }
                }
                _items.value = list
            }
        }
    }
}