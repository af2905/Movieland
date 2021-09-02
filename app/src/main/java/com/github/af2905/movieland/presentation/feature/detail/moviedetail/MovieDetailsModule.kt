package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieId(fragment: MovieDetailsFragment) : MovieDetailsFragmentArgs = fragment.args
}