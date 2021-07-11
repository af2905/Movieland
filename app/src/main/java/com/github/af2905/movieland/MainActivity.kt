package com.github.af2905.movieland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.af2905.movieland.databinding.ActivityMainBinding
import com.github.af2905.movieland.domain.usecase.movies.GetNowPlayingMovies
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.movies.GetTopRatedMovies
import com.github.af2905.movieland.domain.usecase.movies.GetUpcomingMovies
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var binding: ActivityMainBinding

    //todo remove later
    @Inject
    lateinit var getPopularMovies: GetPopularMovies
    //todo remove later
    @Inject
    lateinit var getNowPlayingMovies: GetNowPlayingMovies
    //todo remove later
    @Inject
    lateinit var getUpcomingMovies: GetUpcomingMovies
    //todo remove later
    @Inject
    lateinit var getTopRatedMovies: GetTopRatedMovies

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //todo remove later
        val scope = CoroutineScope(Job())
        //todo remove later
        scope.launch {
            getPopularMovies().movies.let {
                Timber.tag("GET_MOVIES").d("Popular movies: $it")
            }
        }
        //todo remove later
        scope.launch {
            getNowPlayingMovies().movies.let {
                Timber.tag("GET_MOVIES").d("Now playing movies: $it")
            }
        }
        //todo remove later
        scope.launch {
            getUpcomingMovies().movies.let {
                Timber.tag("GET_MOVIES").d("Upcoming movies: $it")
            }
        }
        //todo remove later
        scope.launch {
            getTopRatedMovies().movies.let {
                Timber.tag("GET_MOVIES").d("Top rated movies: $it")
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}