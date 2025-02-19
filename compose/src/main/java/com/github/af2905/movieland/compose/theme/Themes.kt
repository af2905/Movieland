package com.github.af2905.movieland.compose.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat
import com.github.af2905.movieland.compose.R
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

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as ComponentActivity).window

            // Set status bar and navigation bar colors
            window.statusBarColor = colors.theme.tintSelection.toArgb()
            window.navigationBarColor = colors.background.inverse.toArgb()

            WindowCompat.getInsetsController(window, window.decorView).apply {
                // Adjust light/dark appearance for status and navigation bars
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = darkTheme
            }
        }
    }

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

enum class Themes(val nameRes: Int) {
    CLASSIC(R.string.genre_classic),
    SCI_FI(R.string.genre_sci_fi),
    HORROR(R.string.genre_horror),
    ADVENTURE(R.string.genre_adventure),
    FANTASY(R.string.genre_fantasy),
    WESTERN(R.string.genre_western),
    DRAMA(R.string.genre_drama),
    MELODRAMA(R.string.genre_melodrama),
    DETECTIVE(R.string.genre_detective),
    THRILLER(R.string.genre_thriller),
    ROMANCE(R.string.genre_romance);

    fun getTheme(): Palette = when (this) {
        CLASSIC -> classicTheme
        SCI_FI -> sciFiTheme
        HORROR -> horrorTheme
        ADVENTURE -> adventureTheme
        FANTASY -> fantasyTheme
        WESTERN -> westernTheme
        DRAMA -> dramaTheme
        MELODRAMA -> melodramaTheme
        DETECTIVE -> detectiveTheme
        THRILLER -> thrillerTheme
        ROMANCE -> romanceTheme
    }
}

private val classicTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFf9a825),
            tintCard = Color(0xFFF2F2F0),
            tintBg = Color(0xFFF4F4F4),
            tintSelection = Color(0xFFECEDEA)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFf9a825),
            tintBg = Color(0xFF1C1C1C),
            tintCard = Color(0xFF383838),
            tintSelection = Color(0xFF383838)
        )
    )
)

private val sciFiTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF8B3FFD),
            tintCard = Color(0xFFF3EFF7),
            tintBg = Color(0xFFF6F3F8),
            tintSelection = Color(0xFFECE8F0)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF8B3FFD),
            tintBg = Color(0xFF17181F),
            tintCard = Color(0xFF303240),
            tintSelection = Color(0xFF303240)
        )
    )
)

private val horrorTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFFD553F),
            tintCard = Color(0xFFF8F5F3),
            tintBg = Color(0xFFFAF8F7),
            tintSelection = Color(0xFFF3F1F0)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFFD553F),
            tintBg = Color(0xFF1E1619),
            tintCard = Color(0xFF403037),
            tintSelection = Color(0xFF403037)
        )
    )
)

private val adventureTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF58C537),
            tintSelection = Color(0xFFEEF3EC),
            tintCard = Color(0xFFF4F7F2),
            tintBg = Color(0xFFFAFCF9)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF5FC241),
            tintBg = Color(0xFF141613),
            tintCard = Color(0xFF242922),
            tintSelection = Color(0xFF242922)
        )
    )
)

private val fantasyTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF54A0ED),
            tintSelection = Color(0xFFE5EAF1),
            tintCard = Color(0xFFEEF2F8),
            tintBg = Color(0xFFFAFCFE)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF54A0ED),
            tintBg = Color(0xFF161A1B),
            tintCard = Color(0xFF3D424A),
            tintSelection = Color(0xFF3D424A)
        )
    )
)

private val westernTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFBFA973),
            tintSelection = Color(0xFFECEDEA),
            tintCard = Color(0xFFF2F2F0),
            tintBg = Color(0xFFF6F6F5)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFBEA17E),
            tintBg = Color(0xFF1C1C1C),
            tintCard = Color(0xFF383838),
            tintSelection = Color(0xFF383838)
        )
    )
)

private val dramaTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF6FC1C0),
            tintSelection = Color(0xFFE9EBF3),
            tintCard = Color(0xFFF1F2F8),
            tintBg = Color(0xFFFAFBFD)
        )

    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF74C3C8),
            tintBg = Color(0xFF1B1C1D),
            tintCard = Color(0xFF322A2C),
            tintSelection = Color(0xFF322A2C)
        )
    )
)

private val melodramaTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFA6C874),
            tintSelection = Color(0xFFEFF3F0),
            tintCard = Color(0xFFF5F8F5),
            tintBg = Color(0xFFFAFCFA)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFA6C874),
            tintBg = Color(0xFF1B1D1C),
            tintCard = Color(0xFF383838),
            tintSelection = Color(0xFF383838)
        )
    )
)

private val detectiveTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF7398BF),
            tintSelection = Color(0xFFDFE3E8),
            tintCard = Color(0xFFE7EBF0),
            tintBg = Color(0xFFF2F4F6)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF819BBB),
            tintBg = Color(0xFF1F2228),
            tintCard = Color(0xFF424957),
            tintSelection = Color(0xFF424957)
        )
    )
)

private val thrillerTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF865138),
            tintSelection = Color(0xFFEFDFDB),
            tintCard = Color(0xFFF6E9E5),
            tintBg = Color(0xFFFBF6F5)
        )

    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFE4A058),
            tintBg = Color(0xFF271112),
            tintCard = Color(0xFF4E2224),
            tintSelection = Color(0xFF4E2224)
        )
    )
)

private val romanceTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFFF80AB),
            tintSelection = Color(0xFFFFEBEE),
            tintCard = Color(0xFFFFF3F5),
            tintBg = Color(0xFFFFFAFB)
        )

    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFE91E63),
            tintBg = Color(0xFF1E1619),
            tintCard = Color(0xFF403037),
            tintSelection = Color(0xFF403037)
        )
    )
)
