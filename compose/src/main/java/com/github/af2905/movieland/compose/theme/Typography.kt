package com.github.af2905.movieland.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.af2905.movieland.compose.R

internal val defaultFontFamily = FontFamily(
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_medium, weight = FontWeight.Medium),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold)
)

@Immutable
data class Typography(
    private val fontFamily: FontFamily = defaultFontFamily,
    private val color: Color = TypeColors.defaultColorsLight.primary,
    val header: TextStyle = TextStyle(
        color = color,
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        lineHeight = 50.sp
    ),
    val display1: TextStyle = TextStyle(
        color = color,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        letterSpacing = 0.82.sp,
        lineHeight = 64.sp
    ),
    val display2: TextStyle = TextStyle(
        color = color,
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        lineHeight = 52.sp
    ),
    val title1: TextStyle = TextStyle(
        color = color,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        lineHeight = 40.sp
    ),
    val title2: TextStyle = TextStyle(
        color = color,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        letterSpacing = 0.36.sp,
        lineHeight = 28.sp
    ),
    val title3: TextStyle = TextStyle(
        color = color,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        letterSpacing = 0.38.sp,
        lineHeight = 24.sp
    ),
    val headline1: TextStyle = TextStyle(
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        letterSpacing = 0.2.sp,
        lineHeight = 18.sp
    ),
    val headline2: TextStyle = TextStyle(
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        letterSpacing = 0.4.sp,
        lineHeight = 16.sp
    ),
    val body: TextStyle = TextStyle(
        color = color,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        letterSpacing = 0.2.sp,
        lineHeight = 22.sp
    ),
    val bodyMedium: TextStyle = TextStyle(
        color = color,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        letterSpacing = 0.2.sp,
        lineHeight = 22.sp
    ),
    val caption1: TextStyle = TextStyle(
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        letterSpacing = (-0.08).sp,
        lineHeight = 16.sp
    ),
    val caption1Medium: TextStyle = TextStyle(
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        letterSpacing = (-0.08).sp,
        lineHeight = 16.sp
    ),
    val caption2: TextStyle = TextStyle(
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = fontFamily,
        letterSpacing = 0.07.sp,
        lineHeight = 13.sp
    ),
    val captionBar: TextStyle = TextStyle(
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = fontFamily,
        letterSpacing = (-0.08).sp,
        lineHeight = 16.sp
    )
)

internal val typography: Typography = Typography()