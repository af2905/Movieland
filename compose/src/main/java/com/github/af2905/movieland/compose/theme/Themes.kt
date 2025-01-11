package com.github.af2905.movieland.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import com.github.af2905.movieland.compose.theme.AppColors.Companion.defaultColorsDark
import com.github.af2905.movieland.compose.theme.AppColors.Companion.defaultColorsLight

private val LocalAppColors = staticCompositionLocalOf { defaultColorsLight }
private val LocalAppDimens = staticCompositionLocalOf { dimens }
private val LocalAppTypography = staticCompositionLocalOf { typography }

@Composable
fun AppTheme(
    palette: Palette = Palette(light = defaultColorsLight, dark = defaultColorsDark),
    darkTheme: Boolean = isSystemInDarkTheme(),
    fonts: FontFamily? = null,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) palette.dark else palette.light
    val dimens = if (LocalConfiguration.current.screenWidthDp > 600) dimens600 else dimens
    val typography = Typography(fonts ?: defaultFontFamily, colors.type.primary)

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppDimens provides dimens,
        LocalAppTypography provides typography,
    ) {
        content()
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val dimens: Dimens
        @Composable
        get() = LocalAppDimens.current
    val typography: Typography
        @Composable
        get() = LocalAppTypography.current
}