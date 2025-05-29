package dev.chapz.apollo.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.chapz.apollo.playback.ApolloPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val apolloPLayer = ApolloPlayer()
    val nowPlayingIndex = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            apolloPLayer.initPlayer(application.applicationContext)
        }
    }
}