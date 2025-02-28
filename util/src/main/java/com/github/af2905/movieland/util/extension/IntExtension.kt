package com.github.af2905.movieland.util.extension

inline val Int.Companion.empty get() = -1

fun Int?.isNullOrEmpty() = (this == null || this == 0)

//TODO localization
fun Int.convertMinutesToHoursAndMinutes(): String {
    return if (this < 60) {
        "${this}m"
    } else {
        val hours = this / 60
        val remainingMinutes = this % 60
        "${hours}h ${remainingMinutes}m"
    }
}