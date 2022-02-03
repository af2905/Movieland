package com.github.af2905.movieland.helper.text

import android.content.Context
import androidx.annotation.StringRes
import com.github.af2905.movieland.helper.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceUiText(@StringRes private val res: Int?) : ParcelableUiText {

    override fun asCharSequence(context: Context): CharSequence {
        return res?.let { context.resources.getText(res) } ?: String.empty
    }
}