package com.github.af2905.movieland.detail.persondetail.di

import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragmentArgs
import dagger.Module
import dagger.Provides

@Module
class PersonDetailModule {

    @Provides
    fun providePersonId(fragment: PersonDetailFragment) : PersonDetailFragmentArgs = fragment.args
}