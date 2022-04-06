package com.github.af2905.movieland.helper.text

import android.content.Context
import kotlinx.parcelize.Parcelize

@Parcelize
data class StringUiText(private val str: String?) : ParcelableUiText {

    override fun asCharSequence(context: Context) = str.orEmpty()
}