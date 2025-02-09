package com.github.af2905.movieland.search.presentation.result

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.github.af2905.movieland.compose.components.search.SearchLine

@Composable
fun SearchResultScreen(
    state: SearchResultState,
    onAction: (SearchResultAction) -> Unit
) {
    Surface {
        SearchLine(
            searchText = state.query,
            placeholder = "Search...2",
            requestFocusOnShow = true,
            onValueChange = { newValue ->
                onAction(SearchResultAction.UpdateQuery(newValue))
            }
        )
    }
}
