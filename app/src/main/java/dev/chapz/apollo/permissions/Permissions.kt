package dev.chapz.apollo.permissions

import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun AudioPermissionRequestButton() {
    val context = LocalContext.current
    val audioPermissionState = remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            audioPermissionState.value = isGranted
        }
    )

    if (audioPermissionState.value) {
        // Permission granted, continue app functionality
        // Your audio related code here
    } else {
        Button(onClick = {
            if (!audioPermissionState.value) requestPermissionLauncher.launch(READ_MEDIA_AUDIO)
        }) {
            Text("Grant access to audio")
        }
    }
}