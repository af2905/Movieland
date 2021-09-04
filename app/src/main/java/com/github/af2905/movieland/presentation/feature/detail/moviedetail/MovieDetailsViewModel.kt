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
import com.github.af2905.movieland.presentation.model.item.MovieDetailsItem
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails,
) : BaseViewModel<AppNavigator>(coroutineDispatcherProvider) {

    private val movieId = args.movieId

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    var movieDetailsItem = MutableLiveData<MovieDetailsItem>()

    val movieDetailsItemClickListener = object : MovieDetailsItem.Listener {
        override fun onLikedClick(item: MovieDetailsItem) {
            movieDetailsItem.postValue(
                movieDetailsItem.value?.copy(liked = !movieDetailsItem.value!!.liked)
            )
        }

        override fun onBackClicked() = navigate { back() }
    }

    init {
        loadData()
    }

    private fun loadData() {
        launchUI {
            val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
            movieDetails.extractData?.let { movieDetailsItem.postValue(it) }
        }
    }
}