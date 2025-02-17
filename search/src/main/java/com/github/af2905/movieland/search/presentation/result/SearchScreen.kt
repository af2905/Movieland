package com.github.af2905.movieland.search.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.af2905.movieland.compose.components.search.SearchLine
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.search.R

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = stringResource(
                    R.string.search
                ),
                hasNavigationBack = false,
                onBackClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colors.background.default)
        ) {
            SearchLine(
                searchText = state.query,
                placeholder = stringResource(id = R.string.search_hint),
                onValueChange = { newValue ->
                    onAction(SearchAction.UpdateQuery(newValue))
                }
            )
        }
    }

    /*Surface {
        SearchLine(
            searchText = state.query,
            placeholder = "Search...2",
            onValueChange = { newValue ->
                onAction(SearchAction.UpdateQuery(newValue))
            }
        )
    }*/
}
