package dev.chapz.apollo.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.chapz.apollo.playback.ApolloPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    val player: ApolloPlayer by inject()

    var permissionsGranted = MutableStateFlow(Pair(false, false))

    init {
        viewModelScope.launch {
            player.initPlayer(application.applicationContext)
        }
    }
}