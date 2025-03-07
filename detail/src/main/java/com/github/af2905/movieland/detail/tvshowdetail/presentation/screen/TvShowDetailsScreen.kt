package com.github.af2905.movieland.detail.tvshowdetail.presentation.screen

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.components.video_player.YouTubeThumbnail
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsAction
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.toSize
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.SocialMediaProvider
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate
import kotlinx.coroutines.launch

@Composable
fun TvShowDetailsScreen(
    state: TvShowDetailsState,
    onAction: (TvShowDetailsAction) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val showTitle by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = if (showTitle) state.tvShow?.name.orEmpty() else "",
                onBackClick = { onAction(TvShowDetailsAction.BackClick) },
                elevation = 0.dp,
                endButtons = {
                    if (!state.isError && !state.isLoading) {
                        Row {
                            IconButton(
                                onClick = {
                                    onAction(TvShowDetailsAction.ToggleLike)

                                    state.tvShow?.liked?.let {
                                        val liked = !state.tvShow.liked

                                        val message = if (liked) {
                                            context.getString(
                                                R.string.added_to_favorites,
                                                state.tvShow.name.orEmpty()
                                            )
                                        } else {
                                            context.getString(
                                                R.string.removed_from_favorites,
                                                state.tvShow.name.orEmpty()
                                            )
                                        }
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (state.tvShow?.liked == true)
                                        Icons.Filled.Bookmark
                                    else
                                        Icons.Outlined.BookmarkBorder,
                                    contentDescription = "",
                                    tint = if (state.tvShow?.liked == true) AppTheme.colors.theme.tint else AppTheme.colors.type.secondary
                                )
                            }
                            //TODO it will be next implementation
                            /*IconButton(onClick = { *//* Handle action *//* }) {
                                Icon(
                                    imageVector = Icons.Outlined.Share,
                                    contentDescription = ""
                                )
                            }*/
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.colors.theme.tintSelection)
            ) {
                when {
                    state.isLoading -> {
                        ShimmerTvShowDetailsScreen()
                    }

                    state.isError -> {
                        EmptyStateView(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(AppTheme.colors.background.default),
                            icon = Icons.Outlined.ErrorOutline,
                            title = stringResource(R.string.oops_something_went_wrong),
                            action = stringResource(R.string.retry),
                            onClick = { /* Retry Action */ }
                        )
                    }

                    else -> {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            item { TvShowBackdrop(state) }
                            item { TvShowInformation(state) }
                            item { TvShowDetails(state) }

                            if (state.videos.isNotEmpty()) {
                                item { TvShowVideos(state.videos, onAction) }
                            }

                            if (state.casts.isNotEmpty()) {
                                item { TvShowCasts(state.casts, onAction) }
                            }

                            if (!state.tvShow?.productionCompanies.isNullOrEmpty()) {
                                item {
                                    ProductionCompanies(
                                        state.tvShow?.productionCompanies.orEmpty(),
                                        onAction
                                    )
                                }
                            }

                            if (state.recommendedTvShows.isNotEmpty()) {
                                item { RecommendedTvShows(state.recommendedTvShows, onAction) }
                            }

                            if (state.similarTvShows.isNotEmpty()) {
                                item { SimilarTvShows(state.similarTvShows, onAction) }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TvShowBackdrop(state: TvShowDetailsState) {
    var dataGroupSize by remember { mutableStateOf(Size.Zero) }
    var imageError by remember { mutableStateOf(false) }

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
            //elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.background.default)
            ) {
                AsyncImage(
                    model = ImageProvider.getImageUrl(state.tvShow?.backdropPath),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .background(AppTheme.colors.background.default),
                    //error = rememberVectorPainter(image = Icons.Outlined.Image),
                    contentScale = ContentScale.Crop,
                    onError = { imageError = true },
                    onSuccess = { imageError = false },
                    onLoading = { imageError = false }
                )

                if (imageError) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = AppTheme.colors.type.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun TvShowInformation(state: TvShowDetailsState) {
    val context = LocalContext.current

    val seasonsText = remember(state.tvShow?.numberOfSeasons) {
        context.resources.getQuantityString(
            R.plurals.seasons,
            state.tvShow?.numberOfSeasons ?: 0,
            state.tvShow?.numberOfSeasons ?: 0
        )
    }

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
            text = state.tvShow?.name.orEmpty(),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title2,
            color = AppTheme.colors.type.secondary
        )
        if (state.tvShow?.voteAverage != null && state.tvShow.voteAverage != 0.0) {
            RatingBar(rating = state.tvShow.voteAverage ?: 0.0)
        }

        val firstDateYear = state.tvShow?.firstAirDate?.getYearFromReleaseDate()
        val lastDateYear = state.tvShow?.lastAirDate?.getYearFromReleaseDate()

        if (firstDateYear != null) {
            val sb = StringBuilder()
            sb.append("$firstDateYear")

            if (lastDateYear != null && firstDateYear != lastDateYear) {
                sb.append(" - ")
                sb.append("$lastDateYear")
            }

            val numberOfSeasons = state.tvShow.numberOfSeasons

            if (numberOfSeasons != null) {
                sb.append(" • ")
                sb.append(seasonsText)
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = sb.toString(),
                textAlign = TextAlign.Center,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.type.secondary
            )
        }

        val genres = state.tvShow?.genre?.joinToString { it.name }

        if (genres != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = genres,
                color = AppTheme.colors.type.secondary
            )
        }


        val productionCountries = state.tvShow?.productionCountries?.joinToString { it.countryName }

        val sb2 = StringBuilder()

        if (productionCountries != null) {
            sb2.append("$productionCountries")
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = sb2.toString(),
            color = AppTheme.colors.type.secondary
        )

        SocialMediaRow(
            context = context,
            homepageUrl = state.tvShow?.homepage,
            socialIds = state.tvShowSocialIds
        )

        AppHorizontalDivider()
    }
}

@Composable
private fun TvShowDetails(state: TvShowDetailsState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (!state.tvShow?.tagline.isNullOrEmpty()) {
            Text(
                text = state.tvShow?.tagline.orEmpty(),
                style = AppTheme.typography.title3,
                color = AppTheme.colors.type.secondary
            )
        }
        if (!state.tvShow?.overview.isNullOrEmpty()) {
            Text(
                text = state.tvShow?.overview.orEmpty(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.type.secondary
            )
        }
    }
}

@Composable
private fun TvShowVideos(videos: List<Video>, onAction: (TvShowDetailsAction) -> Unit) {

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
                        onAction(TvShowDetailsAction.OpenVideo(videoId))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TvShowCasts(casts: List<CreditsCast>, onAction: (TvShowDetailsAction) -> Unit) {
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
                        onAction(TvShowDetailsAction.OpenPersonDetail(cast.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ProductionCompanies(
    companies: List<ProductionCompany>,
    onAction: (TvShowDetailsAction) -> Unit
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
private fun RecommendedTvShows(
    recommendedTvShows: List<TvShow>,
    onAction: (TvShowDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.recommended_tv_shows),
            action = stringResource(R.string.view_all),
            onClick = { onAction(TvShowDetailsAction.OpenTvShowsByType(tvShowType = TvShowType.RECOMMENDED)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendedTvShows) { tvShow ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = tvShow.name,
                    imageUrl = ImageProvider.getImageUrl(tvShow.posterPath),
                    rating = tvShow.voteAverage,
                    mediaType = MediaType.TV,
                    onItemClick = {
                        onAction(TvShowDetailsAction.OpenTvShowDetail(tvShow.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SimilarTvShows(similarTvShows: List<TvShow>, onAction: (TvShowDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = stringResource(R.string.similar_tv_shows),
            action = stringResource(R.string.view_all),
            onClick = { onAction(TvShowDetailsAction.OpenTvShowsByType(tvShowType = TvShowType.SIMILAR)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(similarTvShows) { tvShow ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = tvShow.name,
                    imageUrl = ImageProvider.getImageUrl(tvShow.posterPath),
                    rating = tvShow.voteAverage,
                    mediaType = MediaType.MOVIE,
                    onItemClick = {
                        onAction(TvShowDetailsAction.OpenTvShowDetail(tvShow.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ShimmerTvShowDetailsScreen() {
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
                .height(194.dp)
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

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

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

        // **TvShow Details Shimmer**
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.7f)
                .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceS))


        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Shimmer for TvShows Casts**
        ShimmerHorizontalList()

        Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))

        // **Shimmer for Recommended TvShows**
        ShimmerHorizontalList()
    }
}

@Composable
private fun ShimmerHorizontalList() {
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
private fun SocialMediaRow(
    context: Context,
    homepageUrl: String?,
    socialIds: TvShowDetailsState.TvShowSocialIds
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!homepageUrl.isNullOrEmpty()) {
                TextButton(onClick = { openUrl(context, homepageUrl) }) {
                    Text(
                        text = AnnotatedString(stringResource(com.github.af2905.movieland.detail.R.string.official_website)),
                        style = TextStyle(
                            color = AppTheme.colors.theme.tint,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(20.dp),
                        tint = AppTheme.colors.theme.tint,
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null
                    )
                }
            }
        }
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
}

@Composable
private fun SocialMediaIcon(
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
private fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}