package com.github.af2905.movieland.detail.persondetail.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment
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
            @BindsInstance personId: Int
        ): PersonDetailComponent
    }
}