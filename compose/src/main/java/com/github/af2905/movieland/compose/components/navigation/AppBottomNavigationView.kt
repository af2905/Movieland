package com.github.af2905.movieland.compose.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun AppBottomNavigationView(
    navController: NavController,
    items: List<BottomNavItem>,
    backgroundColor: Color = AppTheme.colors.theme.tintBg,
    alwaysShowLabel: Boolean = true,
    onItemClick: (BottomNavItem) -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(AppNavRoutes.MainHome.route) }
    NavigationBar(
        containerColor = backgroundColor,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item.route,
                onClick = {
                    onItemClick(item)
                    if (selectedTab == item.route) {
                        navController.navigate(item.route) {
                            popUpTo(item.route) { inclusive = true }
                        }
                    } else {
                        selectedTab = item.route
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { TabIcon(item) },
                label = {
                    Text(
                        text = item.text,
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

@Composable
private fun TabIcon(item: BottomNavItem) {
    Box(modifier = Modifier.size(AppTheme.dimens.iconBottomBarSize)) {
        Icon(
            imageVector = item.icon,
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