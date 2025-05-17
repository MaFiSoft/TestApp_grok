package com.example.testapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TestAppTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = Color.Blue,
        secondary = Color.Blue,
        background = Color.White
    )
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
