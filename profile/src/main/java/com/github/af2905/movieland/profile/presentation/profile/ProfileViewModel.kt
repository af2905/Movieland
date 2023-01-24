package com.github.af2905.movieland.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.model.item.ProfileMenuItem
import com.github.af2905.movieland.core.common.model.item.ProfileMenuItemType
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : ViewModel() {

    val container: Container<ProfileContract.State, ProfileContract.Effect> =
        Container(viewModelScope, ProfileContract.State.Init)

    init {
        container.intent {
            container.reduce {
                ProfileContract.State.Content(list = ProfileMenuItem.getList())
            }
        }
    }

    fun navigateToOption(type: ProfileMenuItemType) {}
}