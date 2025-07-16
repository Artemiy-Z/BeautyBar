package ru.beauty.bar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/*private val DarkColorScheme = darkColorScheme(
    // TODO: dark theme colors
)*/

private val LightColorScheme = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightOnColor,
    primaryContainer = lightPrimaryContainer,
    onPrimaryContainer = lightOnColor,

    secondary = lightSecondary,
    onSecondary = lightOnColor,
    secondaryContainer = lightSecondaryContainer,
    onSecondaryContainer = lightOnColor,

    tertiary = lightTertiary,
    onTertiary = lightOnColor,
    tertiaryContainer = lightTertiaryContainer,
    onTertiaryContainer = lightOnColor,

    error = lightError,
    onError = lightOnError,
    errorContainer = lightErrorContainer,
    onErrorContainer = lightOnErrorContainer,

    surfaceDim = lightSurfaceDim,
    surface = lightSurface,
    inversePrimary = lightSurfaceBright,
    onSurface = lightOnSurface,
    surfaceContainerLowest = lightSurfaceContainerAll,
    surfaceContainerLow = lightSurfaceContainerAll,
    surfaceContainer = lightSurfaceContainerAll,
    surfaceContainerHigh = lightSurfaceContainerAll,
    surfaceContainerHighest = lightSurfaceContainerAll,

    inverseSurface = lightInverseSurface,
    inverseOnSurface = lightInverseOnSurface,

    outline = lightOutline,
)

@Composable
fun BeautyBarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}