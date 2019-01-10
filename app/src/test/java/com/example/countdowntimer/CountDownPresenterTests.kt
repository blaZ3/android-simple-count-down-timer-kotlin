package com.example.countdowntimer

import android.os.Handler
import com.example.countdowntimer.app.countDownScreen.CountDownContract
import com.example.countdowntimer.app.countDownScreen.CountDownPresenter
import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.ValueFormatter
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class CountDownPresenterTests {

    private val MAX_TIME = 2 * 60 *1000L
    private val startTime = 2 * 60 *1000L

    private lateinit var presenter: CountDownContract.Presenter
    private lateinit var view: CountDownContract.View
    private lateinit var stringFetcher: StringFetcherI
    private lateinit var valueFormatter: ValueFormatter
    private lateinit var timer: TimerI

    @Before
    fun setup(){
        timer = Timer()
        view = mock()
        stringFetcher = mock()
        valueFormatter = ValueFormatter(stringFetcher)

        presenter = CountDownPresenter(
            view = view,
            timer = timer,
            stringFetcher = stringFetcher,
            valueFormatter = valueFormatter,
            logger = mock()
        )
    }


    @Test
    fun `when CountDownPresenter startCountDown is called view updateTimer should be called 10 times`() {
        presenter.startCountDown(startTime, MAX_TIME)
        Handler().postDelayed({
            verify(view, times(10)).updateTimer(any())
            presenter.stopCountDown()
        }, 1000)
    }

    @Test
    fun `when CountDownPresenter incrementTimer is called view updateTimer should be called once`(){
        presenter.startCountDown(startTime, MAX_TIME)
        Handler().postDelayed({
            presenter.incrementTimer()
            verify(view, times(1)).updateTimer(any())
            presenter.stopCountDown()
        }, 1000)
    }

    @Test
    fun `when CountDownPresenter incrementTimer is called stringFetcher getString should be called once`(){
        presenter.startCountDown(startTime, MAX_TIME)
        Handler().postDelayed({
            presenter.incrementTimer()
            verify(stringFetcher, times(1)).getString(R.string.str_time_changed)
            presenter.stopCountDown()
        }, 1000)
    }

}