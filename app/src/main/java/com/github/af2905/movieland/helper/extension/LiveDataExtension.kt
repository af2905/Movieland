package com.github.af2905.movieland.helper.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    postValue(value)
}