package com.github.af2905.movieland.compose.components.video_player

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

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

                    // Enable Safe Browsing only on API 26+
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

