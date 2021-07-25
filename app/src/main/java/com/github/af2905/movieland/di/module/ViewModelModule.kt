package com.github.af2905.movieland.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.af2905.movieland.di.ViewModelFactory
import com.github.af2905.movieland.di.ViewModelKey
import com.github.af2905.movieland.presentation.MainViewModel
import com.github.af2905.movieland.presentation.detail.moviedetail.MovieDetailViewModel
import com.github.af2905.movieland.presentation.home.HomeViewModel
import com.github.af2905.movieland.presentation.profile.ProfileViewModel
import com.github.af2905.movieland.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    fun movieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}