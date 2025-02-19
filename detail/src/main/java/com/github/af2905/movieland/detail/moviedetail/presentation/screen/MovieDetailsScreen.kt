package com.github.af2905.movieland.detail.moviedetail.presentation.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.chips.ChipViewStyle
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.components.video_player.YouTubeThumbnail
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.SocialMediaProvider
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsAction
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsState
import com.github.af2905.movieland.util.extension.convertMinutesToHoursAndMinutes
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val showTitle by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = if (showTitle) state.movie?.title.orEmpty() else "",
                onBackClick = { onAction(MovieDetailsAction.BackClick) },
                endButtons = {
                    if (!state.isError && !state.isLoading) {
                        Row {
                            IconButton(onClick = { /* Handle action */ }) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkBorder,
                                    contentDescription = ""
                                )
                            }
                            IconButton(onClick = { /* Handle action */ }) {
                                Icon(
                                    imageVector = Icons.Outlined.Share,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.colors.theme.tintCard)
            ) {
                when {
                    state.isLoading -> {
                        // **Shimmer Loading Screen**
                        ShimmerMovieDetailsScreen()
                    }

                    state.isError -> {
                        // **Error Screen**
                        EmptyStateView(
                            modifier = Modifier.fillMaxSize(),
                            icon = Icons.Outlined.ErrorOutline,
                            title = stringResource(R.string.oops_something_went_wrong),
                            action = stringResource(R.string.retry),
                            onClick = { /* Retry Action */ }
                        )
                    }

                    else -> {
                        // **Success Screen with LazyColumn**
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            item { MovieBackdrop(state) }
                            item { MovieInformation(state) }
                            item { MovieDetails(state) }

                            if (state.videos.isNotEmpty()) {
                                item { MovieVideos(state.videos, onAction) }
                            }

                            if (state.casts.isNotEmpty()) {
                                item { MovieCasts(state.casts, onAction) }
                            }

                            if (!state.movie?.productionCompanies.isNullOrEmpty()) {
                                item {
                                    ProductionCompanies(
                                        state.movie?.productionCompanies.orEmpty(),
                                        onAction
                                    )
                                }
                            }

                            if (state.recommendedMovies.isNotEmpty()) {
                                item { RecommendedMovies(state.recommendedMovies, onAction) }
                            }

                            if (state.similarMovies.isNotEmpty()) {
                                item { SimilarMovies(state.similarMovies, onAction) }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ShimmerMovieDetailsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(AppTheme.colors.background.default)
            .padding(AppTheme.dimens.spaceM)
    ) {
        // **Backdrop Shimmer**
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
        )

        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Title & Info Shimmer**
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(200.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceS))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceS))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceS))

            LazyRow(
                contentPadding = PaddingValues(all = AppTheme.dimens.spaceM),
                horizontalArrangement = Arrangement.Center
            ) {
                items(4) {
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(30.dp, 30.dp)
                            .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusXS))
                    )
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

            Spacer(
                modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusXS))
            )

        }

        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Movie Details Shimmer**
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.7f)
                .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceS))


        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Shimmer for Movie Casts**
        ShimmerHorizontalList()

        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Shimmer for Recommended Movies**
        ShimmerHorizontalList()
    }
}

/**
 * Generic shimmer row to simulate cast/recommendations/similar movies.
 */
@Composable
fun ShimmerHorizontalList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) {
            Spacer(
                modifier = Modifier
                    .size(120.dp, 180.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
            )
        }
    }
}

@Composable
fun MovieBackdrop(state: MovieDetailsState) {
    var dataGroupSize by remember { mutableStateOf(Size.Zero) }

    Box(contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { (dataGroupSize.height / 2).toDp() })
                .background(AppTheme.colors.background.default)
        )

        ElevatedCard(
            enabled = false,
            onClick = { },
            modifier = Modifier

                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .onGloballyPositioned { dataGroupSize = it.size.toSize() },
            shape = RoundedCornerShape(AppTheme.dimens.radiusM),
            colors = CardDefaults.elevatedCardColors(
                containerColor = AppTheme.colors.theme.tintCard
            ),
            elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
        ) {
            AsyncImage(
                model = ImageProvider.getImageUrl(state.movie?.backdropPath),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .background(AppTheme.colors.background.default),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MovieInformation(state: MovieDetailsState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.movie?.title.orEmpty(),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title2,
            color = AppTheme.colors.type.secondary
        )
        if (state.movie?.voteAverage != null && state.movie.voteAverage != 0.0) {
            RatingBar(rating = state.movie.voteAverage ?: 0.0)
        }

        val releaseYear = state.movie?.releaseDate?.getYearFromReleaseDate()
        val genres = state.movie?.genres?.joinToString { it.name }

        val sb1 = StringBuilder()

        if (releaseYear != null) {
            sb1.append("$releaseYear")
        }
        if (releaseYear != null && genres != null) {
            sb1.append(" • ")
        }
        if (genres != null) {
            sb1.append(genres)
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = sb1.toString(),
            color = AppTheme.colors.type.secondary
        )

        val productionCountries = state.movie?.productionCountries?.joinToString { it.countryName }
        val runtime = state.movie?.runtime?.convertMinutesToHoursAndMinutes()

        val sb2 = StringBuilder()

        if (productionCountries != null) {
            sb2.append("$productionCountries")
        }
        if (productionCountries != null && runtime != null) {
            sb2.append(" • ")
        }
        if (runtime != null) {
            sb2.append(runtime)
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = sb2.toString(),
            color = AppTheme.colors.type.secondary
        )

        SocialMediaRow(state.movieSocialIds)

        AppHorizontalDivider()
    }
}

@Composable
fun MovieDetails(state: MovieDetailsState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (!state.movie?.tagline.isNullOrEmpty()) {
            Text(
                text = state.movie?.tagline.orEmpty(),
                style = AppTheme.typography.title3,
                color = AppTheme.colors.type.secondary
            )
        }
        if (!state.movie?.overview.isNullOrEmpty()) {
            Text(
                text = state.movie?.overview.orEmpty(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.type.secondary
            )
        }
    }
}

@Composable
fun MovieVideos(videos: List<Video>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.videos)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(videos) { video ->
                YouTubeThumbnail(
                    videoId = video.key,
                    videoName = video.name,
                    onVideoClick = { videoId ->
                        onAction(MovieDetailsAction.OpenVideo(videoId))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MovieCasts(casts: List<CreditsCast>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.casts)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(casts) { cast ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = cast.name,
                    subtitle = cast.character,
                    showSubtitle = true,
                    imageUrl = ImageProvider.getImageUrl(cast.profilePath),
                    mediaType = MediaType.PERSON,
                    onItemClick = {
                        onAction(MovieDetailsAction.OpenPersonDetail(cast.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProductionCompanies(
    companies: List<ProductionCompany>,
    onAction: (MovieDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.production_companies)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(companies) { item ->
                ChipView(
                    text = item.companyName,
                    isLarge = true,
                    enabled = false
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecommendedMovies(recommendedMovies: List<Movie>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.recommended_movies),
            action = stringResource(R.string.view_all),
            onClick = { onAction(MovieDetailsAction.OpenMoviesByType(movieType = MovieType.RECOMMENDED)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendedMovies) { movie ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = movie.title,
                    imageUrl = ImageProvider.getImageUrl(movie.posterPath),
                    rating = movie.voteAverage,
                    mediaType = MediaType.MOVIE,
                    onItemClick = {
                        onAction(MovieDetailsAction.OpenMovieDetail(movie.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SimilarMovies(similarMovies: List<Movie>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.similar_movies),
            action = stringResource(R.string.view_all),
            onClick = { onAction(MovieDetailsAction.OpenMoviesByType(movieType = MovieType.SIMILAR)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(similarMovies) { movie ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = movie.title,
                    imageUrl = ImageProvider.getImageUrl(movie.posterPath),
                    rating = movie.voteAverage,
                    mediaType = MediaType.MOVIE,
                    onItemClick = {
                        onAction(MovieDetailsAction.OpenMovieDetail(movie.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SocialMediaRow(socialIds: MovieDetailsState.MovieSocialIds) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        socialIds.instagramId?.let { id ->
            SocialMediaProvider.getInstagramUrl(id)?.let { url ->
                SocialMediaIcon(
                    iconRes = R.drawable.ic_instagram,
                    url = url,
                    contentDescription = stringResource(R.string.instagram)
                )
            }
        }

        socialIds.facebookId?.let { id ->
            SocialMediaProvider.getFacebookUrl(id)?.let { url ->
                SocialMediaIcon(
                    iconRes = R.drawable.ic_facebook,
                    url = url,
                    contentDescription = stringResource(R.string.facebook)
                )
            }
        }

        socialIds.twitterId?.let { id ->
            SocialMediaProvider.getTwitterUrl(id)?.let { url ->
                SocialMediaIcon(
                    iconRes = R.drawable.ic_x_twitter,
                    tint = AppTheme.colors.type.secondary,
                    url = url,
                    contentDescription = stringResource(R.string.twitter)
                )
            }
        }

        socialIds.wikidataId?.let { id ->
            SocialMediaProvider.getWikidataUrl(id)?.let { url ->
                SocialMediaIcon(
                    iconRes = R.drawable.ic_wiki,
                    tint = AppTheme.colors.type.secondary,
                    url = url,
                    contentDescription = stringResource(R.string.wikidata)
                )
            }
        }
    }
}

@Composable
fun SocialMediaIcon(
    @DrawableRes iconRes: Int,
    url: String,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
) {
    val context = LocalContext.current

    IconButton(
        onClick = { openUrl(context, url) }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(30.dp)
        )
    }
}

// Helper function to open URLs
fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShimmerMovieDetailsScreenPreview() {
    ShimmerMovieDetailsScreen()
}


