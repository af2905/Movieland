package com.github.af2905.movieland.detail.persondetail.presentation.screen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.cards.ItemCardHorizontal
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.common.helper.SocialMediaProvider
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.PersonCreditsCast
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsAction
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsState
import com.github.af2905.movieland.detail.moviedetail.presentation.screen.SocialMediaIcon
import com.github.af2905.movieland.detail.moviedetail.presentation.screen.openUrl
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonDetailsScreen(
    state: PersonDetailsState,
    onAction: (PersonDetailsAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val showTitle by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = if (showTitle) state.person?.name.orEmpty() else "",
                onBackClick = { onAction(PersonDetailsAction.BackClick) },
                elevation = 0.dp
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.colors.theme.tintSelection)
            ) {
                when {
                    //state.isLoading -> ShimmerPersonDetailsScreen()
                    state.isError -> EmptyStateView(
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Outlined.ErrorOutline,
                        title = stringResource(R.string.oops_something_went_wrong),
                        action = stringResource(R.string.retry),
                        onClick = { /* Retry */ }
                    )

                    else -> LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item { PersonBackdrop(state) }
                        item { PersonInformation(state) }
                        item { PersonBiography(state) }
                        if (state.credits.isNotEmpty()) item {
                            PersonCredits(
                                state.credits,
                                onAction
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PersonBackdrop(state: PersonDetailsState) {
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
                .width(200.dp)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .onGloballyPositioned { dataGroupSize = it.size.toSize() },
            shape = RoundedCornerShape(AppTheme.dimens.radiusM),
            colors = CardDefaults.elevatedCardColors(
                containerColor = AppTheme.colors.theme.tintCard
            )
        ) {
            AsyncImage(
                model = ImageProvider.getImageUrl(state.person?.profilePath),
                contentDescription = null,
                modifier = Modifier
                    .height(220.dp)
                    .background(AppTheme.colors.background.default),
                error = rememberVectorPainter(image = Icons.Outlined.Person),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonInformation(state: PersonDetailsState) {
    val context = LocalContext.current
    val birthAndAgeText = context.formatPersonAgeAndDates(
        birthday = state.person?.birthday,
        deathDay = state.person?.deathDay
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = state.person?.name.orEmpty(),
            style = AppTheme.typography.title2,
            color = AppTheme.colors.type.secondary
        )

        Text(
            text = birthAndAgeText,
            color = AppTheme.colors.type.secondary
        )

        Text(
            text = listOfNotNull(
                state.person?.placeOfBirth,
                state.person?.knownForDepartment
            ).joinToString(" • "),
            color = AppTheme.colors.type.secondary
        )

        SocialMediaRow(
            context = context,
            homepageUrl = state.person?.homepage,
            socialIds = state.personSocialIds
        )
        AppHorizontalDivider()
    }
}

@Composable
fun PersonBiography(state: PersonDetailsState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (!state.person?.biography.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.biography),
                style = AppTheme.typography.title3,
                color = AppTheme.colors.type.secondary
            )
            Text(
                text = state.person?.biography.orEmpty(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.type.secondary
            )
        }
    }
}

@Composable
fun PersonCredits(credits: List<PersonCreditsCast>, onAction: (PersonDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(text = stringResource(R.string.credits))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(all = 16.dp)
        ) {
            credits.forEach { credit ->
                ItemCardHorizontal(
                    title = credit.title.orEmpty(),
                    description = credit.overview.orEmpty(),
                    rating = credit.voteAverage,
                    imageUrl = ImageProvider.getImageUrl(credit.posterPath),
                    onItemClick = {
                        onAction(
                            PersonDetailsAction.OpenCredit(
                                credit.id,
                                credit.mediaType
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SocialMediaRow(
    context: Context,
    homepageUrl: String?,
    socialIds: PersonDetailsState.PersonSocialIds
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if(!homepageUrl.isNullOrEmpty()) {
                TextButton(onClick = { openUrl(context, homepageUrl) }) {
                    Text(
                        text = AnnotatedString(stringResource(R.string.official_website)),
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
            modifier = Modifier
                .padding(bottom = 16.dp)
                .then(
                    if (homepageUrl == null) Modifier.padding(top = 16.dp) else Modifier
                )
        ) {
            // Open URLs only if IDs exist
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

            socialIds.tiktokId?.let { id ->
                SocialMediaProvider.getTiktokUrl(id)?.let { url ->
                    SocialMediaIcon(
                        iconRes = R.drawable.ic_tiktok,
                        tint = AppTheme.colors.type.secondary,
                        url = url,
                        contentDescription = stringResource(R.string.tiktok)
                    )
                }
            }

            socialIds.youtubeId?.let { id ->
                SocialMediaProvider.getYoutubeUrl(id)?.let { url ->
                    SocialMediaIcon(
                        iconRes = R.drawable.ic_youtube,
                        url = url,
                        contentDescription = stringResource(R.string.youtube)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPersonDetailsScreen() {
    val mockPerson = PersonDetail(
        id = 1,
        name = "Leonardo DiCaprio",
        birthday = "1974-11-11",
        knownForDepartment = "Acting",
        deathDay = null,
        gender = 1,
        popularity = 98.5,
        biography = "Leonardo Wilhelm DiCaprio is an American actor and film producer.",
        placeOfBirth = "Los Angeles, California, USA",
        profilePath = "/jrUvWNqOpfDm3D0KhbuRwQOy8Nu.jpg",
        adult = false,
        homepage = "https://www.leonardodicaprio.com",
        liked = false,
        personCreditsCasts = listOf(
            PersonCreditsCast(
                id = 100,
                title = "Inception",
                posterPath = "/path/to/inception.jpg",
                mediaType = MediaType.MOVIE,
                adult = false,
                backdropPath = "/path/to/inception.jpg",
                genreIds = listOf(12),
                originalLanguage = null,
                originalTitle = "",
                overview = "Bla bla bla",
                popularity = 2.3,
                releaseDate = "",
                video = false,
                voteAverage = 7.6,
                voteCount = 12,
                character = "Character",
                creditId = null,
                order = null,
            ),
            PersonCreditsCast(
                id = 101,
                title = "The Wolf of Wall Street",
                posterPath = "/path/to/wolf.jpg",
                mediaType = MediaType.MOVIE,
                adult = false,
                backdropPath = "/path/to/wolf.jpg",
                genreIds = listOf(12),
                originalLanguage = null,
                originalTitle = "",
                overview = "Bla bla bla",
                popularity = 2.3,
                releaseDate = "",
                video = false,
                voteAverage = 7.6,
                voteCount = 12,
                character = "Character",
                creditId = null,
                order = null,
            )
        )
    )

    val mockState = PersonDetailsState(
        person = mockPerson,
        credits = mockPerson.personCreditsCasts,
        personSocialIds = PersonDetailsState.PersonSocialIds(
            facebookId = "LeonardoDiCaprio",
            instagramId = "leonardodicaprio",
            twitterId = "LeoDiCaprio",
            wikidataId = "Q62789",
            tiktokId = "leodicaprio",
            youtubeId = "LeoDiCaprioOfficial"
        )
    )

    PersonDetailsScreen(
        state = mockState,
        onAction = {}
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun Context.formatPersonAgeAndDates(
    birthday: String?,
    deathDay: String?
): String {
    if (birthday.isNullOrEmpty()) return ""

    return try {
        val birthDate = LocalDate.parse(birthday)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        val birthFormatted = birthDate.format(formatter)

        if (!deathDay.isNullOrEmpty()) {
            val deathDate = LocalDate.parse(deathDay)
            val deathFormatted = deathDate.format(formatter)
            getString(R.string.date_range, birthFormatted, deathFormatted)
        } else {
            val birthdayStr = getString(R.string.date_one, birthFormatted)
            val age = Period.between(birthDate, LocalDate.now()).years
            val ageStr = getString(R.string.years_old, age)
            listOfNotNull(birthdayStr, ageStr).joinToString(" • ")
        }
    } catch (e: Exception) {
        birthday
    }
}






