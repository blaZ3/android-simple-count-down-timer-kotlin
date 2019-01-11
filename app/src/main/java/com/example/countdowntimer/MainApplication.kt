package com.example.countdowntimer

import android.app.Application
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, AppModule().modules)
    }

    companion object {
        const val MAX_TIME: Long = 2 * 60 * 1000L //2 mins in milliseconds
    }
}