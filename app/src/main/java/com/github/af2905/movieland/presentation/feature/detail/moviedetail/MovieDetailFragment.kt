package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentMovieDetailBinding
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.BaseFragment

class MovieDetailFragment : BaseFragment<AppNavigator, FragmentMovieDetailBinding, MovieDetailViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_movie_detail
    override fun viewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java
    override fun getNavigator(navController: NavController) = AppNavigator (navController)
}