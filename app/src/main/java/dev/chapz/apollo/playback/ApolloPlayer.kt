package dev.chapz.apollo.playback

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.atomic.AtomicBoolean

class ApolloPlayer : Player.Listener {

    private lateinit var _mediaController: MediaController
    val mediaController: MediaController
        get() = _mediaController
    val playerState = MutableStateFlow(0)
    val isPlaying = MutableStateFlow(false)
    var isReady = AtomicBoolean(false)

    fun initPlayer(context: Context) {
        val sessionToken = SessionToken(context, ComponentName(context, MediaPlayerService::class.java))
        val mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({
            _mediaController = mediaControllerFuture.get()
            _mediaController.prepare()
            _mediaController.addListener(this)
            isReady.set(true)
        }, ContextCompat.getMainExecutor(context))
    }

    override fun onEvents(player: Player, events: Player.Events) {
        playerState.value = player.playbackState
        isPlaying.value = player.isPlaying
    }

}