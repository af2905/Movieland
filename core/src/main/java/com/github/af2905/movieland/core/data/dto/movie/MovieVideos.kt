package com.github.af2905.movieland.core.data.dto.movie

import com.google.gson.annotations.SerializedName

data class MovieVideosDto(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<VideoDto>
)

data class VideoDto(
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String, // YouTube video ID
    @SerializedName("site") val site: String, // YouTube, Vimeo, etc.
    @SerializedName("size") val size: Int, // 720, 1080, etc.
    @SerializedName("type") val type: String, // Trailer, Teaser, Featurette
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("id") val videoId: String
)