package dev.skrilltrax.blockka.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = primaryDark,
  primaryVariant = primaryDark,
  secondary = secondary,
  onSecondary = primaryLight,
  background = primaryDark,
)

private val LightColorPalette = lightColors(
  primary = primaryLight,
  primaryVariant = primaryLight,
  secondary = secondary,
  onSecondary = primaryLight,
  background = primaryLight,
  onPrimary = Color.Black,
  surface = primaryLight,
  onSurface = Color.Black,
  secondaryVariant = secondaryDark,
  onBackground = Color.Black

)

@Composable
fun BlockkaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}
