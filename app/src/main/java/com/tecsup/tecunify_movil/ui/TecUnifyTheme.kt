package com.tecsup.tecunify_movil.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0077C8),      // azul Tecsup
    onPrimary = Color.White,
    secondary = Color(0xFF005A9E),
    onSecondary = Color.White,
    tertiary = Color(0xFF5EC6F2),
    background = Color(0xFFF5F7FB),
    surface = Color.White,
    onBackground = Color(0xFF101318),
    onSurface = Color(0xFF101318)
)

// PALETA OSCURA
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5EC6F2),
    onPrimary = Color(0xFF001526),
    secondary = Color(0xFF0077C8),
    onSecondary = Color.White,
    tertiary = Color(0xFF00B5E2),
    background = Color(0xFF020817),
    surface = Color(0xFF050B1F),
    onBackground = Color(0xFFE4E9F5),
    onSurface = Color(0xFFE4E9F5)
)

@Composable
fun TecUnifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,   // usa el Typography de tu Typography.kt
        content = content
    )
}