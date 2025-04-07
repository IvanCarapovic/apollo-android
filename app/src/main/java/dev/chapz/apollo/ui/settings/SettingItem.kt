package dev.chapz.apollo.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.LibraryMusic
import androidx.compose.material.icons.twotone.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Setting (
    val icon: ImageVector,
    val title: String,
    val description: String,
    val onClick: () -> Unit
)

val settingList = listOf(
    Setting(
        icon = Icons.TwoTone.LibraryMusic,
        title = "Library",
        description = "Manage your library",
        onClick = {}
    ),
    Setting(
        icon = Icons.TwoTone.Palette,
        title = "Appearance",
        description = "Customize the look and feel",
        onClick = {}
    ),
    Setting(
        icon = Icons.TwoTone.Info,
        title = "About",
        description = "Information about the app",
        onClick = {}
    )
)

@Composable
fun SettingItem(setting: Setting) {
    Row(
        modifier = Modifier.clickable(true, onClick = setting.onClick).padding(vertical = 12.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = setting.icon,
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
            )
        ) {
            Text(
                text = setting.title,
                fontSize = 20.sp,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
            Text(
                text = setting.description,
                fontSize = 16.sp,
                color = if(isSystemInDarkTheme()) Color.Gray else Color.DarkGray
            )
        }
    }
}