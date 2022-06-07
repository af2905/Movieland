package com.github.af2905.movieland.presentation.di

import com.github.af2905.movieland.di.module.MainActivityModule
import com.github.af2905.movieland.di.scope.ActivityScope
import com.github.af2905.movieland.presentation.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [MainActivityModule::class])
interface MainComponent {

    fun injectMainActivity(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(): MainComponent
    }
}