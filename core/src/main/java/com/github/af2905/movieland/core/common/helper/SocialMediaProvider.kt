package com.github.af2905.movieland.core.common.helper

object SocialMediaProvider {
    private const val WIKIDATA_BASE_URL = "https://www.wikidata.org/wiki/"
    private const val FACEBOOK_BASE_URL = "https://www.facebook.com/"
    private const val INSTAGRAM_BASE_URL = "https://www.instagram.com/"
    private const val TWITTER_BASE_URL = "https://twitter.com/"
    private const val TIKTOK_BASE_URL = "https://www.tiktok.com/@"
    private const val YOUTUBE_BASE_URL = "https://www.youtube.com/"

    fun getWikidataUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$WIKIDATA_BASE_URL$it" }

    fun getFacebookUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$FACEBOOK_BASE_URL$it" }

    fun getInstagramUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$INSTAGRAM_BASE_URL$it" }

    fun getTwitterUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$TWITTER_BASE_URL$it" }

    fun getTiktokUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$TIKTOK_BASE_URL$it" }

    fun getYoutubeUrl(id: String?): String? = id?.takeIf { it.isNotBlank() }?.let { "$YOUTUBE_BASE_URL$it" }
}
