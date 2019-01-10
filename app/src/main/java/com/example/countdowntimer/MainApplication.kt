package com.example.countdowntimer

import android.app.Application
import com.example.countdowntimer.helpers.logger.AppLogger
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.AppStringFetcher
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI

class MainApplication: Application() {

    lateinit var logger: LoggerI
    lateinit var stringFetcher: StringFetcherI

    override fun onCreate() {
        super.onCreate()

        logger = AppLogger(BuildConfig.DEBUG)
        stringFetcher = AppStringFetcher(applicationContext)
    }


    companion object {
        val MAX_TIME: Long = 2 * 60 *1000L //2 mins in milliseconds
    }
}