package com.github.af2905.movieland.helper.text

fun UIText.Companion.of(res: Int?): ParcelableUIText = ResourceUIText(res)

fun UIText.Companion.of(str: String?): ParcelableUIText = StringUIText(str)