package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.LoadingItem
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails
) : BaseViewModel<AppNavigator>(coroutineDispatcherProvider) {

    private val movieId = args.movieId

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    private fun starter() = listOf(LoadingItem())

    init {
        _items.postValue(starter())
        loadData()
    }

    private fun loadData() {
        launchUI {
            val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
            val result = movieDetails.extractData?.let {
                listOf(it, emptySpaceMedium)
            }
            _items.postValue(result ?: emptyList())
        }
    }
}