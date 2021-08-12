package com.github.af2905.movieland.data.result

enum class HttpResponseCode(val errorCode: IntRange) {
    OK(200..299),
    CLIENT_ERROR(400..499),
    SERVER_ERROR(500..526)
}