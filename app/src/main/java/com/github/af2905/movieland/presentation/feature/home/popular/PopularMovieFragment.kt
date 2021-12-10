package com.github.af2905.movieland.presentation.feature.home.popular

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentPopularMovieBinding
import com.github.af2905.movieland.presentation.base.BaseFragment
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator

class PopularMovieFragment :
    BaseFragment<HomeNavigator, FragmentPopularMovieBinding, PopularMovieViewModel>() {

    override fun getNavigator(navController: NavController) = HomeNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_popular_movie
    override fun viewModelClass(): Class<PopularMovieViewModel> = PopularMovieViewModel::class.java

}