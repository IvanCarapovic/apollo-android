package dev.chapz.apollo.playback

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import kotlinx.coroutines.flow.MutableStateFlow

class ApolloPlayer : Player.Listener {

    private lateinit var _mediaController: MediaController
    val mediaController: MediaController
        get() = _mediaController
    val playerState = MutableStateFlow(0)
    val isPlaying = MutableStateFlow(false)
    val nowPlayingIndex = MutableStateFlow(0)

    fun initPlayer(context: Context) {
        val sessionToken = SessionToken(context, ComponentName(context, MediaPlayerService::class.java))
        val mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({
            _mediaController = mediaControllerFuture.get()
            _mediaController.prepare()
            _mediaController.addListener(this)
        }, ContextCompat.getMainExecutor(context))
    }

    override fun onEvents(player: Player, events: Player.Events) {
        playerState.value = player.playbackState
        isPlaying.value = player.isPlaying
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        // when the song changes, regardless of manual or automatic, update the nowPlayingIndex
        nowPlayingIndex.value = mediaController.currentMediaItemIndex
    }
}