package com.github.af2905.movieland.presentation.detail.moviedetail

import com.github.af2905.movieland.R
import com.github.af2905.movieland.base.BaseFragment
import com.github.af2905.movieland.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_movie_detail
    override fun viewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java

}