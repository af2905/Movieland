package com.github.af2905.movieland.helper.extension

fun Double.fiveStarRating(): Double = this / 2

fun Double.formatVoteAverage(digits: Int) = "%.${digits}f".format(this)