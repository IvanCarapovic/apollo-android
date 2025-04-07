package dev.chapz.apollo.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(paddingValues: PaddingValues) {
    Box(modifier = Modifier.padding(paddingValues)) {
        LazyColumn(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
            content = {
                items(settingList.size) { index ->
                    SettingItem(setting = settingList[index])
                }
            }
        )
    }
}