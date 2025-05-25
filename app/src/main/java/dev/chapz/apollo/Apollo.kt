package dev.chapz.apollo

import android.app.Application
import android.os.StrictMode

class Apollo : Application() {

    override fun onCreate() {
        super.onCreate()

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