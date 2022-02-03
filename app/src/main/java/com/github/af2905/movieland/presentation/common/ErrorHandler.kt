package com.github.af2905.movieland.presentation.common

import com.github.af2905.movieland.R
import com.github.af2905.movieland.data.error.ConnectionException
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.helper.text.of
import timber.log.Timber



object ErrorHandler {

    fun handleError(throwable: Throwable?) : UiText {
        Timber.e(throwable)
        return when (throwable) {
            is ConnectionException -> UiText.of(R.string.fail_to_connect)
            else -> UiText.of(throwable?.message)
        }
    }
}