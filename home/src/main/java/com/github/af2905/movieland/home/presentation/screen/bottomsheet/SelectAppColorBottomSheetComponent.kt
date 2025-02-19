package com.github.af2905.movieland.home.presentation.screen.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.components.bottomsheet.SelectableItem
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.home.R

@Composable
fun SelectAppColorBottomSheetComponent(
    modifier: Modifier = Modifier,
    currentTheme: Themes,
    list: List<Themes>,
    onItemClick: (theme: Themes) -> Unit
) {

    Column(
        modifier = modifier.padding(bottom = 20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.select_theme_color),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title3,
            color = AppTheme.colors.type.secondary
        )

        LazyColumn(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.name }
            ) { index, model ->
                SelectableItem(
                    title = stringResource(id = model.nameRes),
                    selected = model.name == currentTheme.name,
                    onClick = { onItemClick(model) },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(20.dp)
                                .background(
                                    color = model.getTheme().dark.theme.tint,
                                    shape = CircleShape
                                )
                        ) {}
                    }
                )
                if (index < list.lastIndex) {
                    HorizontalDivider(color = AppTheme.colors.background.divider)
                }
            }
        }
    }
}