package com.example.countdowntimer

import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.logger.AppLogger
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.AppStringFetcher
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

class AppModule {

    private val appModule = module {

        single<StringFetcherI> { AppStringFetcher(androidContext()) }
        single<LoggerI> { (isDebug: Boolean) -> AppLogger(isDebug) }
        single { ValueFormatter(get()) }

    }

    private val timerModule = module {
        factory<TimerI> { Timer() }
    }

    val modules = listOf(
        appModule,
        timerModule
    )
}