package com.github.af2905.movieland.detail.persondetail

import dagger.Module
import dagger.Provides

@Module
class PersonDetailModule {

    @Provides
    fun providePersonId(fragment: PersonDetailFragment) : PersonDetailFragmentArgs = fragment.args
}