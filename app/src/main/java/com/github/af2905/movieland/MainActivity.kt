package com.github.af2905.movieland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.af2905.movieland.databinding.ActivityMainBinding
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
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
    @Inject
    lateinit var getMovieDetails: GetMovieDetails


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //todo remove later
        val scope = CoroutineScope(Job())
        //todo remove later
        scope.launch {
            val channel = Channel<Int>()

            launch {
                getPopularMovies().movies.let {
                    Timber.tag("GET_MOVIES").d("Popular movies: $it \n --------------")
                    channel.send(it.first().id)
                }
            }
           launch {
               getMovieDetails(channel.receive()).let {
                   Timber.tag("GET_MOVIES").d("Movie details: $it")
               }
           }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}