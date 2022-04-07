package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {

    @Provides
    fun provideMovieId(fragment: MovieDetailFragment) : MovieDetailFragmentArgs = fragment.args
}