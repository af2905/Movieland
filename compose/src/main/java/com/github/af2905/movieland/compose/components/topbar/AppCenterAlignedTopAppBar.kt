package com.github.af2905.movieland.compose.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.modifier.clickableUnbounded
import com.github.af2905.movieland.compose.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    hasNavigationBack: Boolean = true,
    elevation: Dp = 0.dp,
    endButtons: @Composable (RowScope.() -> Unit) = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.theme.tint)
            .shadow(elevation, shape = RectangleShape)
    ) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = AppTheme.typography.title3,
                    color = AppTheme.colors.type.secondary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = AppTheme.colors.theme.tintCard,
                navigationIconContentColor = AppTheme.colors.type.secondary,
                actionIconContentColor = AppTheme.colors.type.secondary,
            ),
            navigationIcon = {
                if (hasNavigationBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = AppTheme.dimens.spaceM)
                            .clickableUnbounded { onBackClick() }
                    )
                }
            },
            actions = {
                endButtons()
                Spacer(modifier = Modifier.width(AppTheme.dimens.spaceM))
            }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun AppCenterAlignedTopAppBarPreview() {
    AppTheme {
        AppCenterAlignedTopAppBar(
            title = "Sample Title",
            onBackClick = { /* Handle back action */ },
            hasNavigationBack = true,
            endButtons = {
                IconButton(onClick = { /* Handle action */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        )
    }
}