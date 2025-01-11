package com.github.af2905.movieland.compose.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun AppBottomNavigationView(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.colors.theme.tintBg,
    alwaysShowLabel: Boolean = true,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomAppBar(
        modifier = modifier.background(backgroundColor),
        containerColor = backgroundColor,
        contentColor = AppTheme.colors.theme.tintGhost
    ) {
        NavigationBar(
            containerColor = backgroundColor,
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = { TabIcon(item) },
                    label = {
                        Text(
                            text = item.text,
                            textAlign = TextAlign.Center,
                            style = AppTheme.typography.captionBar
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppTheme.colors.theme.tint,
                        unselectedIconColor = AppTheme.colors.background.iconBar,
                        selectedTextColor = AppTheme.colors.theme.tint,
                        unselectedTextColor = AppTheme.colors.background.iconBar
                    ),
                    alwaysShowLabel = alwaysShowLabel
                )
            }
        }
    }
}

@Composable
private fun TabIcon(item: BottomNavItem) {
    Box(modifier = Modifier.size(AppTheme.dimens.iconBottomBarSize)) {
        Icon(
            painter = painterResource(item.imageId),
            contentDescription = item.text,
            modifier = Modifier.size(AppTheme.dimens.iconBottomBarSize)
        )
        if (item.hasBadge) {
            Spacer(
                modifier = Modifier
                    .size(AppTheme.dimens.spaceXS)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = AppTheme.dimens.space2XS,
                        y = -AppTheme.dimens.space2XS
                    )
                    .background(
                        color = AppTheme.colors.background.alert,
                        shape = CircleShape
                    )
            )
        }
    }
}