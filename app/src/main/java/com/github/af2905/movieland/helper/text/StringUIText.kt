package com.github.af2905.movieland.helper.text

import android.content.Context
import kotlinx.parcelize.Parcelize

@Parcelize
data class StringUIText(private val str: String?) : ParcelableUIText {

    override fun asCharSequence(context: Context) = str.orEmpty()
}