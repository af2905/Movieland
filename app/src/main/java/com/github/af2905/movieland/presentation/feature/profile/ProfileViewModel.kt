package com.github.af2905.movieland.presentation.feature.profile

import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.feature.search.SearchContract
import com.github.af2905.movieland.presentation.model.item.UserInfoHeaderItem
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<SearchContract.State, SearchContract.Effect>(coroutineDispatcherProvider) {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.Loading())

    val userInfoHeader = UserInfoHeaderItem()

    init {

    }
}