package com.github.af2905.movieland.core.di

import android.content.Context

interface CoreComponentStore {
    fun getComponent(): CoreComponent
}

object CoreComponentProvider {
    fun getAppComponent(context: Context): CoreComponent {
        return (context.applicationContext as CoreComponentStore).getComponent()
    }
}