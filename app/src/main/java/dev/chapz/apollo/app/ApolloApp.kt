package dev.chapz.apollo.app

import android.app.Application
import android.content.Intent
import android.os.StrictMode
import dev.chapz.apollo.playback.MediaPlayerService

class ApolloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // start the MediaPlayerService as early as possible
        startService(Intent(this, MediaPlayerService::class.java))

        applyDevelopmentStrictMode()
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