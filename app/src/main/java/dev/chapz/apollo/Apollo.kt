package dev.chapz.apollo

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.StrictMode
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import dev.chapz.apollo.player.MediaPlayerService
import java.util.concurrent.ExecutionException

class Apollo : Application() {

    private var future: ListenableFuture<MediaController>? = null

    /**
     * Controls the media playback throughout the app.
     * */
    var mediaController: MediaController? = null

    override fun onCreate() {
        super.onCreate()

        initializeAudioSystem()
        applyDevelopmentStrictMode()
    }

    private fun initializeAudioSystem() {
        // start the MediaPlayerService
        startService(Intent(this, MediaPlayerService::class.java))

        // get MediaController up and running
        val sessionToken = SessionToken(this, ComponentName(this, MediaPlayerService::class.java))
        future = MediaController.Builder(this, sessionToken).buildAsync()
        future?.addListener({
            try {
                mediaController = future!!.get()
                Log.i("MediaController", "MediaController ready")
            } catch (e: ExecutionException) {
                Log.e("MediaController", "Failed to obtain MediaController:\n" + e.stackTraceToString())
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun applyDevelopmentStrictMode() {
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyFlashScreen()
            .build()

        StrictMode.setThreadPolicy(threadPolicy)

        val vmPolicy = StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyDeath()
            .build()

        StrictMode.setVmPolicy(vmPolicy)
    }
}

/**
 * Extension method to easily get the media controller
 * anywhere in the app where there is a context available.
 *
 * @return The [MediaController] singleton.
 * */
fun Context.getMediaController(): MediaController {
    return (applicationContext as Apollo).mediaController!!
}