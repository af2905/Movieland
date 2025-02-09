package com.github.af2905.movieland.search.presentation.search

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.github.af2905.movieland.compose.components.search.SearchLine

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    Surface {
        SearchLine(
            searchText = state.query,
            placeholder = "Search...1",
            onValueChange = {},
            onClick = {
                println("SEARCH_TAG click")
                onAction(SearchAction.StartSearch)
            }
        )
    }
}
