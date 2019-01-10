package com.example.countdowntimer

import android.os.Handler
import com.example.countdowntimer.app.countDown.CountDownContract
import com.example.countdowntimer.app.countDown.CountDownPresenter
import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.app.timer.TimerI
import com.example.countdowntimer.helpers.stringFetcher.StringFetcherI
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class CountDownPresenterTests {

    private val MAX_TIME = 2 * 60 *1000L
    private val startTime = 2 * 60 *1000L

    private lateinit var presenter: CountDownContract.Presenter
    private lateinit var view: CountDownContract.View
    private lateinit var stringFetcher: StringFetcherI
    private lateinit var timer: TimerI

    @Before
    fun setup(){
        timer = Timer()
        view = mock()
        stringFetcher = mock()

        presenter = CountDownPresenter(
            view = view,
            timer = timer,
            stringFetcher = stringFetcher,
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