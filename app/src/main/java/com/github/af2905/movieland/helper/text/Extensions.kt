package com.github.af2905.movieland.helper.text

fun UiText.Companion.of(res: Int?): ParcelableUiText = ResourceUiText(res)

fun UiText.Companion.of(str: String?): ParcelableUiText = StringUiText(str)