package com.example.countdowntimer

import com.example.countdowntimer.app.countDownScreen.CountDownContract
import com.example.countdowntimer.app.countDownScreen.CountDownPresenter
import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.logger.AppLogger
import com.example.countdowntimer.helpers.logger.LoggerI
import com.example.countdowntimer.helpers.stringFetcher.AppStringFetcher
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

class AppModule {

    private val appModule = module {

        single<StringFetcherI> { AppStringFetcher(androidContext()) }
        single<LoggerI> { (isDebug: Boolean) -> AppLogger(isDebug) }
        single { ValueFormatter(get()) }

    }

    private val countDown = module {
        factory<CountDownContract.Presenter> { (view: CountDownContract.View, isDebug: Boolean) -> CountDownPresenter(
            view = view,
            stringFetcher = get(),
            valueFormatter = get (),
            logger = get { parametersOf(isDebug) },
            timer = get()
        ) }
    }

    private val timerModule = module {
        factory<TimerI> { Timer() }
    }

    val modules = listOf(
        appModule,
        countDown,
        timerModule
    )
}