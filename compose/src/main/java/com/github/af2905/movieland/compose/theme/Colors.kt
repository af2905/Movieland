package com.github.af2905.movieland.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

data class Palette(val light: AppColors, val dark: AppColors)

@Immutable
data class AppColors(
    val type: TypeColors = TypeColors(),
    val background: BackgroundColors = BackgroundColors()
) {
    companion object {
        val defaultColorsLight = AppColors(
            type = TypeColors.defaultColorsLight,
            background = BackgroundColors.defaultColorsLight
        )

        val defaultColorsDark = AppColors(
            type = TypeColors.defaultColorsDark,
            background = BackgroundColors.defaultColorsDark
        )
    }
}

@Immutable
data class TypeColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val ghost: Color = Color.Unspecified,
) {
    companion object {
        val defaultColorsLight = TypeColors(
            primary = Color(0xFF2C3848),
            secondary = Color(0xFFFCD307),
            ghost = Color(0xFFB3B3B3)
        )

        val defaultColorsDark = TypeColors(
            primary = Color(0xFF4169E1),
            secondary = Color(0xFFFCD307),
            ghost = Color(0xFF5E5E5E)
        )
    }
}

@Immutable
data class BackgroundColors(
    val default: Color = Color.Unspecified,
    val divider: Color = Color.Unspecified,
    val actionRipple: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val black: Color = Color.Unspecified
) {
    companion object {
        val defaultColorsLight = BackgroundColors(
            default = Color(0xFFF7F7F7),
            divider = Color(0x0D000000),
            actionRipple = Color(0x14000000),
            white = Color(0xFFFFFFFF),
            black = Color(0xFF1A1A1A)
        )

        val defaultColorsDark = BackgroundColors(
            default = Color(0xFF18191A),
            divider = Color(0x0DFFFFFF),
            actionRipple = Color(0x14FFFFFF),
            white = Color(0xFFFFFFFF),
            black = Color(0xFF1A1A1A)
        )
    }
}