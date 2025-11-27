package com.tecsup.tecunify_movil.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF243386),      // azul TECSUP
    secondary = Color(0xFF00B5E2),
    background = Color(0xFF050816),
    surface = Color(0xFF0B1020),
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF243386),
    secondary = Color(0xFF00B5E2),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onBackground = Color(0xFF050816),
    onSurface = Color(0xFF050816)
)

@Composable
fun TecUnifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}