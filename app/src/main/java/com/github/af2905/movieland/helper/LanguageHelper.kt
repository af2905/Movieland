package com.github.af2905.movieland.helper

import java.util.*

private const val RUSSIAN = "ru"
private const val ENGLISH = "en"

object LanguageHelper {

    private fun getCurrentLanguage() = Locale.getDefault().language.toString()

    fun getRussianLanguageOrDefault() = when (getCurrentLanguage()) {
        RUSSIAN -> RUSSIAN
        else -> ENGLISH
    }
}