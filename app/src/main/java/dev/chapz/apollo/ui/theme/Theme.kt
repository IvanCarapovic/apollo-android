package dev.chapz.apollo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ApolloTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        Build.VERSION.SDK_INT > Build.VERSION_CODES.VANILLA_ICE_CREAM ->
            if (darkTheme) dynamicDarkColorScheme(LocalContext.current) else expressiveLightColorScheme()

        else ->
            if (darkTheme) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}