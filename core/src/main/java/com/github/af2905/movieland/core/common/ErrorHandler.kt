package com.github.af2905.movieland.core.common

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.text.UiText
import com.github.af2905.movieland.core.common.text.of
import com.github.af2905.movieland.core.data.error.ConnectionException
import timber.log.Timber

object ErrorHandler {

    fun handleError(throwable: Throwable?) : UiText {
        Timber.e(throwable)
        return when (throwable) {
            is ConnectionException -> UiText.of(R.string.message_fail_to_connect)
            else -> UiText.of(throwable?.message)
        }
    }
}