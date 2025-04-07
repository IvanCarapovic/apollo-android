package dev.chapz.apollo.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material.icons.twotone.PrivacyTip
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header() {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text("Apollo")
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(imageVector = Icons.TwoTone.Search, contentDescription = "Search")
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.TwoTone.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Refresh") },
                    leadingIcon = { Icon(Icons.TwoTone.Refresh, contentDescription = null)},
                    onClick = { /* Handle option 1 click */ expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Privacy policy") },
                    leadingIcon = { Icon(Icons.TwoTone.PrivacyTip, contentDescription = null)},
                    onClick = { /* Handle option 2 click */ expanded = false }
                )
            }
        }
    )
}