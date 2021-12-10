package com.github.af2905.movieland.presentation.feature.home.top

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentTopRatedMovieBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator

class TopRatedMovieFragment :
    BaseFragment<HomeNavigator, FragmentTopRatedMovieBinding, TopMovieViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_top_rated_movie
    override fun viewModelClass(): Class<TopMovieViewModel> =
        TopMovieViewModel::class.java
}