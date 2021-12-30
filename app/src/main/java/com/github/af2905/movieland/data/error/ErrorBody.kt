package com.github.af2905.movieland.data.error

import com.github.af2905.movieland.domain.Name
import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName(Name.CODE) val code: Int?,
    @SerializedName(Name.ERROR) val error: String?
)