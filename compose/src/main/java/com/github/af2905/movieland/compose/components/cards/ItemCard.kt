package com.github.af2905.movieland.compose.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.theme.AppTheme

private fun Double?.orDefault(): Double = this ?: 0.0

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String?,
    rating: Double?,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick() },
        modifier = modifier.width(150.dp),
        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
        colors = CardDefaults.elevatedCardColors(
            containerColor = AppTheme.colors.theme.tintCard
        ),
        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            RatingBar(
                rating = rating.orDefault(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.spaceXS)
                        then (if (rating == null) Modifier.alpha(0.0f) else Modifier)
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            Text(
                text = title.orEmpty(),
                style = AppTheme.typography.caption1,
                color = AppTheme.colors.type.secondary,
                minLines = 1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.dimens.spaceXS,
                        end = AppTheme.dimens.spaceXS,
                        bottom = AppTheme.dimens.spaceXS
                    )
                    .then(
                        if (title == null) Modifier.alpha(0.0f) else Modifier
                    )
            )
        }
    }
}

@Composable
fun ItemCardLarge(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String?,
    rating: Double?,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick() },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
        colors = CardDefaults.elevatedCardColors(
            containerColor = AppTheme.colors.theme.tintCard
        ),
        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.height(200.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space2XS)
            ) {
                RatingBar(
                    rating = rating.orDefault(),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = AppTheme.dimens.spaceS)
                            then (
                            if (rating == null) Modifier.alpha(0.0f) else Modifier)
                )
                Text(
                    text = title.orEmpty(),
                    style = AppTheme.typography.bodyMedium,
                    minLines = 1,
                    maxLines = 1,
                    color = AppTheme.colors.type.secondary,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = AppTheme.dimens.spaceS,
                            end = AppTheme.dimens.spaceXS,
                            bottom = AppTheme.dimens.spaceXS
                        )
                )
            }
        }
    }
}

@Composable
fun ItemCardHorizontal(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String?,
    rating: Double?,
    icon: ImageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
    itemTypeName: String? = null,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick() },
        modifier = modifier
            .height(150.dp),
        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
        colors = CardDefaults.elevatedCardColors(
            containerColor = AppTheme.colors.theme.tintCard
        ),
        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )

            Column(Modifier.padding(all = AppTheme.dimens.spaceS)) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = title.orEmpty(),
                        style = AppTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .then(
                                if (title == null) Modifier.alpha(0.0f) else Modifier
                            )
                    )

                    Box(modifier = Modifier.padding(start = AppTheme.dimens.spaceXS)) {
                        if (itemTypeName == null) {
                            Image(
                                imageVector = icon,
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(
                                    color = AppTheme.colors.type.disable
                                ),
                            )
                        } else {
                            ChipView(text = itemTypeName, enabled = false)
                        }
                    }
                }
                Spacer(
                    modifier = if (rating == null) {
                        Modifier.height(AppTheme.dimens.spaceM)
                    } else {
                        Modifier.height(AppTheme.dimens.spaceXS)
                    }
                )

                RatingBar(
                    rating = rating.orDefault(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (rating == null) Modifier.alpha(0.0f) else Modifier
                        )
                )

                Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

                Text(
                    text = title.orEmpty(),
                    style = AppTheme.typography.caption1,
                    color = AppTheme.colors.type.secondary,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .then(
                            if (title == null) Modifier.alpha(0.0f) else Modifier
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewItemCardHorizontal() {
    AppTheme() {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            ItemCardHorizontal(
                modifier = Modifier.padding(all = 16.dp),
                title = "aaa Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
                rating = 6.7,
                onItemClick = {}
            )
            ItemCardHorizontal(
                modifier = Modifier.padding(all = 16.dp),
                title = "Lion King Lion King Lion King Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
                rating = 9.7,
                itemTypeName = "Movie",
                onItemClick = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewItemCardLarge() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            ItemCardLarge(
                modifier = Modifier.padding(all = 16.dp),
                title = "Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
                rating = 6.7,
                onItemClick = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewItemCard() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            ItemCard(
                modifier = Modifier.padding(all = 16.dp),
                title = "Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
                rating = 6.7,
                onItemClick = {}
            )
        }
    }
}