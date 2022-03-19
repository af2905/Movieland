package com.github.af2905.movieland.helper.extension

inline val Double.Companion.empty get() = 0.0

fun Double.fiveStarRating(): Double = this / 2

fun Double.formatVoteAverage(digits: Int) = "%.${digits}f".format(this)