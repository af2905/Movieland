package com.github.af2905.movieland.compose.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun SelectableItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    textColor: Color? = null,
    textDecoration: TextDecoration? = null,
    leadingContent : @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        leadingContent?.let {
            leadingContent()
        }
        Text(
            text = title,
            style = AppTheme.typography.body,
            overflow = TextOverflow.Ellipsis,
            textDecoration = textDecoration,
            color = textColor ?: AppTheme.colors.type.secondary,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Icon(
            modifier = Modifier
                .padding(end = 20.dp)
                .padding(vertical = 10.dp)
                .then(
                    if (!selected) Modifier.alpha(0f) else Modifier
                ),
            imageVector = Icons.Outlined.Check,
            contentDescription = "",
            tint = AppTheme.colors.theme.tint
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComponentPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SelectableItem(
            title = "Item1",
            selected = true,
            onClick = {},
            textDecoration = null,
            textColor = null
        )
        SelectableItem(
            title = "Item2",
            selected = false,
            onClick = {},
            textDecoration = null,
            textColor = null
        )
    }
}