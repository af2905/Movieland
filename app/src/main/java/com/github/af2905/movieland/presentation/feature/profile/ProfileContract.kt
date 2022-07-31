package com.github.af2905.movieland.presentation.feature.profile

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.UserInfoHeaderItem

class ProfileContract {

    sealed class State : UiState() {

        object Init : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(
            val list: List<Model>,
            val userInfoHeader: UserInfoHeaderItem = UserInfoHeaderItem()
        ) : State()

        fun toContent(): Content? = if (this is Content) this else null
    }


    sealed class Effect : UiEffect() {

        data class OpenMenuItemDetail(val navigator: Navigate) : Effect()
    }
}