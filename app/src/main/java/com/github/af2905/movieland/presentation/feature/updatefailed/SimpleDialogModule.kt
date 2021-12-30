package com.github.af2905.movieland.presentation.feature.updatefailed

import com.github.af2905.movieland.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SimpleDialogModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun updateFailedFragment(): SimpleDialogFragment
}