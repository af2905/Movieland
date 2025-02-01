package com.github.af2905.movieland.compose.components.video_player

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun YouTubePlayerWithThumbnail(videoId: String) {
    var showWebView by rememberSaveable { mutableStateOf(false) }

    if (!showWebView) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clickable { showWebView = true } // Switch to WebView on click
        ) {
            AsyncImage(
                model = "https://img.youtube.com/vi/$videoId/hqdefault.jpg",
                contentDescription = "YouTube Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .padding(8.dp)
            )
        }
    } else {
        if (LocalInspectionMode.current) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "WebView not supported in Preview", color = Color.White)
            }
        } else {
            YouTubePlayer(videoId)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlayer(videoId: String) {
    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                settings.apply {
                    javaScriptEnabled = true  // Required for YouTube iframe API
                    domStorageEnabled = true  // Needed for embedded YouTube videos
                    allowFileAccess = false   // Blocks access to local files
                    allowContentAccess = false // Restricts content access
                    builtInZoomControls = false
                    displayZoomControls = false
                    mediaPlaybackRequiresUserGesture = false
                    mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        safeBrowsingEnabled = true
                    }
                }

                // Restrict navigation to YouTube ONLY
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        val url = request?.url?.toString() ?: ""
                        return !url.startsWith("https://www.youtube.com/")
                    }
                    override fun onReceivedError(
                        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                    ) {
                        Log.e("WebViewError", "Error: ${error?.description}")
                    }
                }

                // Load YouTube Embed (Sanitized)
                val safeHtml = """
                    <html>
                    <body style="margin:0;padding:0;">
                        <iframe width="100%" height="100%" 
                            src="https://www.youtube.com/embed/$videoId" 
                            frameborder="0" allowfullscreen>
                        </iframe>
                    </body>
                    </html>
                """.trimIndent()

                loadDataWithBaseURL(
                    "https://www.youtube.com",
                    safeHtml,
                    "text/html",
                    "utf-8",
                    null
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

@Preview(showBackground = true)
@Composable
fun YouTubePlayerWithThumbnailPreview() {
    YouTubePlayerWithThumbnail(videoId = "Jr4RL-fpC7k") // Example video ID
}