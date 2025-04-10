package dev.chapz.apollo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    Box(modifier = Modifier.padding(paddingValues)) {
        SongList()
    }
}