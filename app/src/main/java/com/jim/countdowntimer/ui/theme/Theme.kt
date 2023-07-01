
package com.jim.countdowntimer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColors(
    surface = md_theme_dark_surfaceVariant,
    onSurface= md_theme_dark_onSurfaceVariant,
    background = md_theme_dark_background
)

private val LightColorScheme = lightColors(
    surface = md_theme_light_surfaceVariant,
    onSurface = md_theme_light_onSurfaceVariant,
    background = md_theme_light_background
)

@Composable
fun CountDownTimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colors = colors,
        typography =Typography,
        shapes = Shapes,
        content = content
    )
}
