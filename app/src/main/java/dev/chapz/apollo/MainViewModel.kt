package dev.chapz.apollo

import android.app.Application
import android.content.ComponentName
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import dev.chapz.apollo.player.MediaPlayerService

/**
 * Shared view model containing classes that are used across the entire app and
 * it's lifecycle.
 * */
class MainViewModel(
    private val application: Application
) : AndroidViewModel(application) {
    private var _mediaController: MediaController? = null
    private var future: ListenableFuture<MediaController>? = null

    /**
     * Controls the [MediaPlayerService].
     * */
    val mediaController: MediaController
        get() = if (_mediaController != null) {
            _mediaController!!
        } else throw IllegalStateException("MediaController provided by MainViewModel seems to be null. Did you forget to call initMediaController()?")

    /**
     * Call this method early in your apps lifecycle to initialize the media controller
     * and make it available to use as soon as the user can interact with the app.
     * */
    fun initMediaController() {
        val ctx = application.applicationContext
        val sessionToken = SessionToken(ctx, ComponentName(ctx, MediaPlayerService::class.java))
        future = MediaController.Builder(ctx, sessionToken).buildAsync()
        future?.addListener({
            _mediaController = future?.get()
            _mediaController!!.playWhenReady = true
            future = null
            Log.d("TAG", "MediaController ready")
        }, ContextCompat.getMainExecutor(ctx))
    }

}