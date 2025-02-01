package com.github.af2905.movieland.compose.components.video_player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun YouTubeThumbnail(
    videoId: String,
    videoName: String,
    onVideoClick: (String) -> Unit
) {
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

    Box(
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .clickable { onVideoClick(videoId) }
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = "YouTube Thumbnail for $videoName",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play $videoName",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                .padding(8.dp)
        )
    }
}