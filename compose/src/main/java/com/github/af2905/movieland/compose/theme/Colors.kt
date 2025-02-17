package com.github.af2905.movieland.compose.theme

import android.graphics.Color.HSVToColor
import android.graphics.Color.colorToHSV
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class Palette(val light: AppColors, val dark: AppColors)

@Immutable
data class AppColors(
    val type: TypeColors = TypeColors(),
    val background: BackgroundColors = BackgroundColors(),
    val theme: ThemeColors = ThemeColors()
) {
    companion object {
        val defaultColorsLight = AppColors(
            type = TypeColors.defaultColorsLight,
            background = BackgroundColors.defaultColorsLight,
            theme = ThemeColors.defaultColorsLight
        )

        val defaultColorsDark = AppColors(
            type = TypeColors.defaultColorsDark,
            background = BackgroundColors.defaultColorsDark,
            theme = ThemeColors.defaultColorsDark
        )
    }
}

@Immutable
data class TypeColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val ghost: Color = Color.Unspecified,
    val inverse: Color = Color.Unspecified,
    val secondaryInverse: Color = Color.Unspecified,
    val ghostInverse: Color = Color.Unspecified,
    val success: Color = Color.Unspecified,
    val alert: Color = Color.Unspecified,
    val black: Color = Color.Unspecified,
    val disable: Color = Color.Unspecified
) {
    companion object {
        val defaultColorsLight = TypeColors(
            primary = Color(0xFF000000),
            secondary = Color(0xFF666666),
            ghost = Color(0xFFB3B3B3),
            inverse = Color(0xFFFFFFFF),
            secondaryInverse = Color(0xFFD1D1D1),
            ghostInverse = Color(0xFF5E5E5E),
            success = Color(0xFF009961),
            alert = Color(0xFFFF3333),
            black = Color(0xFF1A1A1A),
            disable = Color(0x4D000000)
        )

        val defaultColorsDark = TypeColors(
            primary = Color(0xFFFFFFFF),
            secondary = Color(0xFFA3A3A3),
            ghost = Color(0xFF5E5E5E),
            inverse = Color(0xFFFFFFFF),
            secondaryInverse = Color(0xFFA3A3A3),
            ghostInverse = Color(0xFF5E5E5E),
            success = Color(0xFF009961),
            alert = Color(0xFFFF3333),
            black = Color(0xFF1A1A1A),
            disable = Color(0x4DFFFFFF)
        )
    }
}

@Immutable
data class BackgroundColors(
    val default: Color = Color.Unspecified,
    val card: Color = Color.Unspecified,
    val divider: Color = Color.Unspecified,
    val border: Color = Color.Unspecified,
    val ghost: Color = Color.Unspecified,
    val tone: Color = Color.Unspecified,
    val inverse: Color = Color.Unspecified,
    val success: Color = Color.Unspecified,
    val alert: Color = Color.Unspecified,
    val ghostAlert: Color = Color.Unspecified,
    val actionRipple: Color = Color.Unspecified,
    val actionRippleInverse: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val black: Color = Color.Unspecified,
    val cardInverse: Color = Color.Unspecified,
    val iconBar: Color = Color.Unspecified,
    val searchBar: Color = Color.Unspecified,
    val searchBarInverse: Color = Color.Unspecified
) {
    companion object {
        val defaultColorsLight = BackgroundColors(
            default = Color(0xFFF7F7F7),
            card = Color(0xFFFFFFFF),
            divider = Color(0x0D000000),
            border = Color(0x1F000000),
            ghost = Color(0xFFEBEBEB),
            tone = Color(0xB2000000),
            inverse = Color(0xFF1A1A1A),
            success = Color(0xFF009961),
            alert = Color(0xFFFF3333),
            ghostAlert = Color(0x26FF3333),
            actionRipple = Color(0x14000000),
            actionRippleInverse = Color(0x29FFFFFF),
            white = Color(0xFFFFFFFF),
            black = Color(0xFF1A1A1A),
            cardInverse = Color(0xFF2B2633),
            iconBar = Color(0x44000000),
            searchBar = Color(0x1A000000),
            searchBarInverse = Color(0xFF18191A)
        )

        val defaultColorsDark = BackgroundColors(
            default = Color(0xFF18191A),
            card = Color(0xFF2A2B2D),
            divider = Color(0x0DFFFFFF),
            border = Color(0x1FFFFFFF),
            ghost = Color(0xFF151515),
            tone = Color(0xB2000000),
            inverse = Color(0xFF080808),
            success = Color(0xFF009961),
            alert = Color(0xFFFF3333),
            ghostAlert = Color(0x26FF3333),
            actionRipple = Color(0x14FFFFFF),
            actionRippleInverse = Color(0x29FFFFFF),
            white = Color(0xFFFFFFFF),
            black = Color(0xFF1A1A1A),
            cardInverse = Color(0xFF2B2633),
            iconBar = Color(0xFF5E5E5E),
            searchBar = Color(0x1AFFFFFF),
            searchBarInverse = Color(0xFFF7F7F7)
        )
    }
}

@Immutable
data class ThemeColors(
    val tint: Color = Color.Unspecified,
    val tintFade: Color = getTintFade(tint),
    val tintGhost: Color = tint.copy(alpha = 0.15f),
    val tintBg: Color = Color.Unspecified,
    val tintCard: Color = Color.Unspecified,
    val tintSelection: Color = getTintSelection(tint),
) {
    companion object {
        val defaultColorsLight = ThemeColors(
            tint = Color(0xFF8B3FFD),
            tintFade = Color(0xFFB08EE4),
            tintGhost = Color(0x268B3FFD),
            tintBg = Color(0xFFECE8F0),
            tintCard = Color(0xFFFFFFFF),
            tintSelection = Color(0xFFBA8CFF)
        )

        val defaultColorsDark = ThemeColors(
            tint = Color(0xFF8B3FFD),
            tintFade = Color(0xFFB08EE4),
            tintGhost = Color(0x268B3FFD),
            tintBg = Color(0xFF17181F),
            tintCard = Color(0xFF303240),
            tintSelection = Color(0xFFBA8CFF)
        )
    }
}

private fun getTintFade(color: Color): Color {
    val hsv = FloatArray(3)
    colorToHSV(color.toArgb(), hsv)
    hsv[1] *= 0.5f
    hsv[2] *= 0.9f
    val newColor = HSVToColor(hsv)
    return Color(newColor)
}

private fun getTintSelection(color: Color): Color {
    val hsv = FloatArray(3)
    colorToHSV(color.toArgb(), hsv)
    hsv[1] *= 0.6f
    hsv[2] *= 1.15f
    val newColor = HSVToColor(hsv)
    return Color(newColor)
}