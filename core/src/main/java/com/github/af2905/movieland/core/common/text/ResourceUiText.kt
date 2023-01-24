package com.github.af2905.movieland.core.common.text

import android.content.Context
import androidx.annotation.StringRes
import com.github.af2905.movieland.util.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceUiText(@StringRes private val res: Int?) : ParcelableUiText {

    override fun asCharSequence(context: Context): CharSequence {
        return res?.let { context.resources.getText(res) } ?: String.empty
    }
}