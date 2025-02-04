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
            window.statusBarColor = colors.theme.tint.toArgb()
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

enum class Themes {
    FANTASY,
    ROMANCE,
    SCI_FI,
    COMEDY,
    THRILLER,
    ACTION,
    ADVENTURE,
    DRAMA,
    MYSTERY,
    WESTERN,
    HORROR,
    ANIMATION,
    WAR;

    fun getTheme(): Palette = when (this) {
        ACTION -> actionTheme
        ADVENTURE -> adventureTheme
        COMEDY -> comedyTheme
        DRAMA -> dramaTheme
        FANTASY -> fantasyTheme
        MYSTERY -> mysteryTheme
        ROMANCE -> romanceTheme
        THRILLER -> thrillerTheme
        WESTERN -> westernTheme
        SCI_FI -> sciFiTheme
        HORROR -> horrorTheme
        ANIMATION -> animationTheme
        WAR -> warTheme
    }
}


private val actionTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF757575), // Steel Gray
            tintBg = Color(0xFFD50000), // Intense Red
            tintCard = Color(0xFFFFFFFF) // Pure White
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF757575), // Metallic Steel
            tintBg = Color(0xFF1C1C1C), // Dark Gunmetal
            tintCard = Color(0xFF2B2B2B) // Charcoal Gray
        )
    )
)

private val adventureTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF1B5E20),
            tintBg = Color(0xFFEEF3EC),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF2E7D32),
            tintBg = Color(0xFF162D12),
            tintCard = Color(0xFF253823)
        )
    )
)

private val comedyTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFFFA000),
            tintBg = Color(0xFFFFF3E0),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFFFA726),
            tintBg = Color(0xFF332212),
            tintCard = Color(0xFF403828)
        )
    )
)

private val dramaTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF303F9F), // Royal Deep Blue
            tintBg = Color(0xFFB0BEC5), // Soft Cool Gray
            tintCard = Color(0xFFFFFFFF) // Classic White
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF1A237E), // Dark Midnight Blue
            tintBg = Color(0xFF1A1A2E), // Midnight Drama Background
            tintCard = Color(0xFF424A5B) // Cool Blue-Gray for Depth
        )
    )
)


private val fantasyTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF7E57C2),
            tintBg = Color(0xFFEBE5FA),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF9575CD),
            tintBg = Color(0xFF2A1A42),
            tintCard = Color(0xFF3A2C5A)
        )
    )
)

private val mysteryTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF37474F),
            tintBg = Color(0xFFECEFF1),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF546E7A),
            tintBg = Color(0xFF212B30),
            tintCard = Color(0xFF37474F)
        )
    )
)

private val romanceTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFFF80AB),
            tintBg = Color(0xFFFFEBEE),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFF06292),
            tintBg = Color(0xFF3B2B2D),
            tintCard = Color(0xFF512D3A)
        )
    )
)

private val thrillerTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFBF360C),
            tintBg = Color(0xFFFFE0B2),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFD84315),
            tintBg = Color(0xFF3D1B0C),
            tintCard = Color(0xFF5A2E22)
        )
    )
)

private val westernTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF795548),
            tintBg = Color(0xFFEFEBE9),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF8D6E63),
            tintBg = Color(0xFF3E2723),
            tintCard = Color(0xFF5D4037)
        )
    )
)

private val sciFiTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF00BCD4),
            tintBg = Color(0xFFE0F7FA),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF26C6DA),
            tintBg = Color(0xFF0D253F),
            tintCard = Color(0xFF183A5C)
        )
    )
)

private val horrorTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFFB71C1C),
            tintBg = Color(0xFFFFEBEE),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFFD32F2F),
            tintBg = Color(0xFF1B1B1B),
            tintCard = Color(0xFF3A272A)
        )
    )
)

private val animationTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF42A5F5), // Playful sky blue (like animated skies)
            tintBg = Color(0xFFE1F5FE), // Soft pastel background
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF64B5F6), // Soft bright blue
            tintBg = Color(0xFF1E3A5F), // Deep night-sky blue
            tintCard = Color(0xFF2A4978) // Animated blue shades
        )
    )
)

private val warTheme = Palette(
    light = defaultColorsLight.copy(
        theme = ThemeColors(
            tint = Color(0xFF4E5D3E),
            tintBg = Color(0xFFE8E5DA),
            tintCard = Color(0xFFFFFFFF)
        )
    ),
    dark = defaultColorsDark.copy(
        theme = ThemeColors(
            tint = Color(0xFF5D6D40),
            tintBg = Color(0xFF2C2F24),
            tintCard = Color(0xFF3D4130)
        )
    )
)
