package com.github.af2905.movieland.compose.components.empty_state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import com.github.af2905.movieland.compose.components.buttons.FlatButtonView
import com.github.af2905.movieland.compose.components.icons.IconVectorFadeView
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    title: String? = null,
    subtitle: String? = null,
    action: String? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        painter?.let {
            IconVectorFadeView(painter = it)
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
        title?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(
                        start = AppTheme.dimens.spaceM,
                        end = AppTheme.dimens.spaceM,
                        bottom = AppTheme.dimens.space2XS
                    ),
                textAlign = TextAlign.Center,
                style = AppTheme.typography.bodyMedium
            )
        }
        subtitle?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(
                        start = AppTheme.dimens.spaceM,
                        end = AppTheme.dimens.spaceM,
                        top = AppTheme.dimens.space2XS
                    ),
                color = AppTheme.colors.type.ghost,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.caption1
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))
        action?.let {
            FlatButtonView(
                text = it,
                onClick = onClick
            )
        }
    }
}