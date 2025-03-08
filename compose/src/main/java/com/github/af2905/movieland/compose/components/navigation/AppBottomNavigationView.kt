package com.github.af2905.movieland.compose.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun AppBottomNavigationView(
    navController: NavController,
    currentTab: String,
    items: List<BottomNavItem>,
    backgroundColor: Color = AppTheme.colors.background.default,
    alwaysShowLabel: Boolean = true,
    elevation: Dp = 0.dp,
    onTabSelected: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .shadow(elevation, shape = RectangleShape)
    ) {
        NavigationBar(
            containerColor = backgroundColor,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentTab == item.route,
                    onClick = {
                        if (currentTab != item.route) {
                            onTabSelected(item.route)
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } else {
                            navController.popBackStack(item.route, inclusive = false)
                        }
                    },
                    icon = { TabIcon(item = item, selected = currentTab == item.route) },
                    label = {
                        Text(
                            text = stringResource(id = item.textRes),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = AppTheme.colors.theme.tintGhost,
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
private fun TabIcon(item: BottomNavItem, selected: Boolean) {
    Box(modifier = Modifier.size(AppTheme.dimens.iconBottomBarSize)) {
        Icon(
            imageVector = if (selected) item.selectedIcon else item.icon,
            contentDescription = stringResource(id = item.textRes),
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