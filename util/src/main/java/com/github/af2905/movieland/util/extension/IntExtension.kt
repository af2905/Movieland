package com.github.af2905.movieland.util.extension

inline val Int.Companion.empty get() = -1

fun Int?.isNullOrEmpty() = (this == null || this == 0)