package dev.chapz.apollo.app

import android.app.Application
import android.os.StrictMode
import dev.chapz.apollo.data.library.Library
import dev.chapz.apollo.playback.ApolloPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class ApolloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val viewmodelModule = module {
            viewModel { MainViewModel(this@ApolloApp) }
        }

        val mediaModule = module {
            single { ApolloPlayer() }
            single { Library(contentResolver) }
        }

        startKoin {
            androidLogger(Level.WARNING)
            androidContext(this@ApolloApp)
            modules(
                mediaModule,
                viewmodelModule
            )
        }

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