package com.github.af2905.movieland.compose.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun SearchLine(
    modifier: Modifier = Modifier,
    requestFocusOnShow: Boolean = false,
    searchText: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    queryFilters: ((String) -> String)? = null,
    onClick: (() -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .background(color = AppTheme.colors.theme.tintCard)
            .padding(horizontal = AppTheme.dimens.spaceM, vertical = AppTheme.dimens.spaceS)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(40.dp)
                .background(color = AppTheme.colors.theme.tintGhost)
                .padding(start = 10.dp, end = 10.dp)
                .clickable(enabled = onClick != null) { onClick?.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.theme.tintFade),
                modifier = Modifier.size(20.dp)
            )

            if (onClick == null) {
                BasicTextField(
                    value = searchText,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    onValueChange = {
                        val query = queryFilters?.invoke(it) ?: it
                        onValueChange(query)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.Transparent)
                        .focusRequester(focusRequester),
                    textStyle = AppTheme.typography.bodyMedium.copy(
                        color = AppTheme.colors.theme.tint
                    ),
                    cursorBrush = SolidColor(AppTheme.colors.theme.tint),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp, 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchText.isBlank()) {
                                Text(
                                    text = placeholder,
                                    style = TextStyle(color = AppTheme.colors.theme.tintFade)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            } else {
                Text(
                    text = searchText.ifBlank { placeholder },
                    style = TextStyle(color = AppTheme.colors.theme.tintFade),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                )
            }

            if (searchText.isNotBlank() && onClick == null) {
                IconButton(onClick = { onValueChange("") }) {
                    Image(
                        imageVector = Icons.Default.Clear,
                        colorFilter = ColorFilter.tint(AppTheme.colors.theme.tintFade),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (requestFocusOnShow && onClick == null) {
            scope.launch {
                focusRequester.requestFocus()
            }
        }
    }
}

@Preview(showBackground = true, name = "🔍 Empty Search Field")
@Composable
private fun PreviewSearchLineEmpty() {
    var searchText by remember { mutableStateOf("") }

    SearchLine(
        searchText = searchText,
        placeholder = "Search...",
        onValueChange = { searchText = it }
    )
}

@Preview(showBackground = true, name = "✍ With Text")
@Composable
private fun PreviewSearchLineWithText() {
    var searchText by remember { mutableStateOf("Action Movies") }

    SearchLine(
        searchText = searchText,
        placeholder = "Search...",
        onValueChange = { searchText = it }
    )
}

@Preview(showBackground = true, name = "🎯 With Query Filter")
@Composable
private fun PreviewSearchLineFiltered() {
    var searchText by remember { mutableStateOf("12345") }

    SearchLine(
        searchText = searchText,
        placeholder = "Search...",
        onValueChange = { searchText = it },
        queryFilters = { query -> query.filter { it.isLetter() } } // Removes numbers
    )
}

@Preview(showBackground = true, name = "🚀 Auto-Focus")
@Composable
private fun PreviewSearchLineAutoFocus() {
    var searchText by remember { mutableStateOf("") }

    SearchLine(
        searchText = searchText,
        placeholder = "Search...",
        onValueChange = { searchText = it },
        requestFocusOnShow = true // Focus automatically
    )
}
