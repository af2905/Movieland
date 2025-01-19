package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genres(
    @PrimaryKey val id: Int,
    val name: String,
    val timeStamp: Long? = null
)