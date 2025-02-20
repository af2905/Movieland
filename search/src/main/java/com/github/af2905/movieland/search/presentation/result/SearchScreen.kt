package com.github.af2905.movieland.search.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.components.cards.ItemCardHorizontal
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.chips.ChipViewStyle
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.search.SearchLine
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.SearchMultiResult
import com.github.af2905.movieland.search.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors().copy(
                    titleContentColor = AppTheme.colors.type.secondary,
                    containerColor = AppTheme.colors.theme.tintSelection,
                    scrolledContainerColor = AppTheme.colors.theme.tintSelection
                ),
                title = {
                    Text(
                        text = stringResource(R.string.search),
                        style = AppTheme.typography.title3,
                        color = AppTheme.colors.type.secondary
                    )
                },
                scrollBehavior = scrollBehavior
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

            when {
                state.isLoading -> {
                    // **Show shimmer while loading**
                    ShimmerSearchResults()
                }

                state.query.isNotEmpty() && state.query.isNotBlank() && state.searchResult.isEmpty() && !state.isLoading -> {
                    // **Show "No results found" when search fails**
                    EmptyStateView(
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Outlined.SearchOff,
                        title = stringResource(R.string.no_results_found)
                    )
                }

                state.query.isBlank() -> {
                    // **Show popular searches when search bar is empty**
                    PopularSearches(state.popularSearches, onAction)
                }

                state.searchResult.isNotEmpty() -> {
                    // **Display search results**
                    LazyColumn(
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.searchResult) { item ->
                            when (item.mediaType) {
                                MediaType.MOVIE -> MovieItem(item, onAction)
                                MediaType.TV -> TvShowItem(item, onAction)
                                MediaType.PERSON -> PersonItem(item, onAction)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopularSearches(popularResults: List<String>, onAction: (SearchAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (popularResults.isNotEmpty()) {
            Text(
                text = stringResource(R.string.popular_searches),
                style = AppTheme.typography.title3,
                color = AppTheme.colors.type.secondary,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            SearchSuggestionChips(suggestions = popularResults, onAction = onAction)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchSuggestionChips(suggestions: List<String>, onAction: (SearchAction) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        suggestions.forEach { title ->
            ChipView(
                style = ChipViewStyle.Inform,
                textStyle = AppTheme.typography.caption1,
                text = title,
                isLarge = true,
                onClick = {
                    onAction(SearchAction.UpdateQuery(title))
                }
            )
        }
    }
}

@Composable
fun ShimmerSearchResults() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        repeat(5) {
            Spacer(
                modifier = Modifier
                    .height(136.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: SearchMultiResult,
    onAction: (SearchAction) -> Unit
) {
    ItemCardHorizontal(
        title = movie.title.orEmpty(),
        description = movie.overview.orEmpty(),
        imageUrl = ImageProvider.getImageUrl(movie.posterPath),
        rating = movie.voteAverage,
        itemTypeName = stringResource(R.string.movie),
        onItemClick = { onAction(SearchAction.OpenMovieDetail(movie.id)) }
    )
}

@Composable
fun TvShowItem(
    tvShow: SearchMultiResult,
    onAction: (SearchAction) -> Unit
) {
    ItemCardHorizontal(
        title = tvShow.name.orEmpty(),
        description = tvShow.overview.orEmpty(),
        imageUrl = ImageProvider.getImageUrl(tvShow.posterPath),
        rating = tvShow.voteAverage,
        itemTypeName = stringResource(R.string.tv_show),
        onItemClick = { onAction(SearchAction.OpenTvShowDetail(tvShow.id)) }
    )
}

@Composable
fun PersonItem(
    person: SearchMultiResult,
    onAction: (SearchAction) -> Unit
) {
    val knownForText = person.knownFor?.joinToString(", ") { it.title ?: it.name ?: "" }.orEmpty()

    ItemCardHorizontal(
        title = person.name.orEmpty(),
        description = stringResource(R.string.known_for, knownForText),
        imageUrl = ImageProvider.getImageUrl(person.profilePath),
        rating = null,
        itemTypeName = stringResource(R.string.person),
        onItemClick = { onAction(SearchAction.OpenPersonDetail(person.id)) }
    )
}


