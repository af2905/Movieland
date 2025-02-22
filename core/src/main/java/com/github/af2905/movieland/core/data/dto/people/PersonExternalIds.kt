package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonExternalIds(
    @SerializedName("id") val id: Int,
    @SerializedName("wikidata_id") val wikidataId: String?,
    @SerializedName("facebook_id") val facebookId: String?,
    @SerializedName("instagram_id") val instagramId: String?,
    @SerializedName("twitter_id") val twitterId: String?,
    @SerializedName("tiktok_id") val tiktokId: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
)