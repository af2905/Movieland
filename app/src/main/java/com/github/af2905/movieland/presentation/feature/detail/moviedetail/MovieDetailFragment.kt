package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentMovieDetailBinding
import com.github.af2905.movieland.presentation.base.BaseFragment

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_movie_detail
    override fun viewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java
}