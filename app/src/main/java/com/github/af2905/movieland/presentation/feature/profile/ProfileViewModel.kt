package com.github.af2905.movieland.presentation.feature.profile

import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<AppNavigator>(coroutineDispatcherProvider) {

}