package com.github.af2905.movieland.presentation.feature.profile

import com.github.af2905.movieland.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment
}