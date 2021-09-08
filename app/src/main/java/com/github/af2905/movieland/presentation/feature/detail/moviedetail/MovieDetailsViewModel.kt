package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
import com.github.af2905.movieland.presentation.model.item.MovieDetailsItem
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails,
    private val getMovieActors: GetMovieActors
) : BaseViewModel<AppNavigator>(coroutineDispatcherProvider) {

    private val movieId = args.movieId

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
            loading.emit(true)
            if (movieDetailsItem.value == null) {
                val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
                movieDetails.extractData?.let { movieDetailsItem.postValue(it) }
            }
            getMovieActors(MovieActorsParams(movieId)).let { list ->
                list.extractData?.let {
                    val actors = it.filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
                    _items.postValue(listOf(HorizontalListItem(actors)))
                }
            }
            loading.emit(false)
        }
    }

    fun refresh() = loadData()

    fun openActorDetail(item: MovieActorItem, position: Int) {}

}