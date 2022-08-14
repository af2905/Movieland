package com.github.af2905.movieland.home.presentation

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.home.presentation.item.HomeMenuPlaceholderItem

class HomeContract {

    sealed class State(open val list: List<Model>) : UiState() {

        data class Init(
            override val list: List<Model> = listOf(HomeMenuPlaceholderItem())
        ) : State(list)

        data class Content(override val list: List<Model>) : State(list)
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMenuItemDetail(val navigator: Navigate) : Effect()
    }
}