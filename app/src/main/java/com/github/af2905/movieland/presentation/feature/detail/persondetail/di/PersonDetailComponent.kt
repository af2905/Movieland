package com.github.af2905.movieland.presentation.feature.detail.persondetail.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailFragment
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailFragmentArgs
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface PersonDetailComponent {

    fun injectPersonDetailFragment(fragment: PersonDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            @BindsInstance args: PersonDetailFragmentArgs
        ): PersonDetailComponent
    }
}