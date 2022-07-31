package com.github.af2905.movieland.core.base

import android.os.Bundle
import androidx.fragment.app.Fragment

class RetainStoreFragment<T> : Fragment() {

    var component: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}