package com.github.af2905.movieland.presentation.feature.detail.persondetail.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailFragment
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailFragmentArgs
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface PersonDetailComponent {

    fun injectPersonDetailFragment(fragment: PersonDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            @BindsInstance args: PersonDetailFragmentArgs
        ): PersonDetailComponent
    }
}