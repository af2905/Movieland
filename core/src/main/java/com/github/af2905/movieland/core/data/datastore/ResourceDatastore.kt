package com.github.af2905.movieland.core.data.datastore

import android.content.Context
import com.github.af2905.movieland.core.R

class ResourceDatastore(private val context: Context) {

    fun getLanguage() = context.getString(R.string.language)
}