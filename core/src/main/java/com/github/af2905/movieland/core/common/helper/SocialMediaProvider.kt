package com.github.af2905.movieland.core.common.helper

object SocialMediaProvider {
    private const val WIKIDATA_BASE_URL = "https://www.wikidata.org/wiki/"
    private const val FACEBOOK_BASE_URL = "https://www.facebook.com/"
    private const val INSTAGRAM_BASE_URL = "https://www.instagram.com/"
    private const val TWITTER_BASE_URL = "https://twitter.com/"

    fun getWikidataUrl(id: String?): String? {
        return id?.let { "$WIKIDATA_BASE_URL$it" }
    }

    fun getFacebookUrl(id: String?): String? {
        return id?.let { "$FACEBOOK_BASE_URL$it" }
    }

    fun getInstagramUrl(id: String?): String? {
        return id?.let { "$INSTAGRAM_BASE_URL$it" }
    }

    fun getTwitterUrl(id: String?): String? {
        return id?.let { "$TWITTER_BASE_URL$it" }
    }
}