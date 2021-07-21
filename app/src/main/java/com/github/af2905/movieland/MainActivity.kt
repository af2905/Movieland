package com.github.af2905.movieland

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.af2905.movieland.databinding.ActivityMainBinding
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //todo remove later
    @Inject
    lateinit var getPopularMovies: GetPopularMovies

    @Inject
    lateinit var getSimilarMovies: GetSimilarMovies


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //todo remove later
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        //todo remove later
        scope.launch {
            val channel = Channel<Int>()

            launch {
                getPopularMovies(PopularMoviesParams())
                    .let {
                        it.extractData?.let { entity ->
                            channel.send(entity.movies.first().id)
                        }
                    }
            }

            launch {
                getSimilarMovies(SimilarMoviesParams(channel.receive())).let {
                    Timber.tag("GET_MOVIES").d("Similar movies: ${it.extractData}")
                }
            }
        }
    }
}