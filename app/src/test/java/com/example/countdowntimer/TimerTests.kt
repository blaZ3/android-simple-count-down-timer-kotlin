package com.example.countdowntimer

import android.os.Handler
import com.example.countdowntimer.app.timer.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class TimerTests {

    private val MAX_TIME = 2 * 60 *1000L
    private val startTime = 2 * 60 *1000L

    private lateinit var timer: Timer

    @Before
    fun setup(){
        timer = Timer()
    }


    @Test
    fun `should raise TimerNotStartedException when timer stop is called before starting`(){
        var exceptionRaised = false

        try {
            timer.stop()
        }catch (ex: TimerNotStartedException){
            exceptionRaised = true
        }


        assert(exceptionRaised)
    }

    @Test
    fun `should return remainingTime when timer stopped after starting`(){
        var exceptionRaised = false
        var remainingTime = 0L

        timer.start(startTime, MAX_TIME, mock())

        try {
            remainingTime = timer.stop()
        }catch (ex: TimerException){
            exceptionRaised = true
        }

        timer.stop()

        assert(!exceptionRaised && remainingTime > 0L)
    }

    @Test
    fun `should call onError with TimerAlreadyStartedException when same timer started twice`(){
        val callback: TimerI.TimerCallback = mock()

        timer.start(startTime, MAX_TIME, callback)
        timer.start(startTime, MAX_TIME, callback)

        verify(callback, times(1)).onError(TimerAlreadyStartedException)
    }

    @Test
    fun `should raise TimerNotStartedException when increaseTimer called before starting`(){
        val callback: TimerI.ChangeTimerCallback = mock()
        timer.increaseTimer(10 * 1000L, callback)
        verify(callback, times(1)).onError(TimerNotStartedException)
    }


    @Test
    fun `should not raise any TimerException when increaseTime is able to increment time`() {
        val callback: TimerI.ChangeTimerCallback = mock()

        timer.start(startTime, MAX_TIME, mock())

        Handler().postDelayed({
            timer.increaseTimer(1000L, callback)
            verify(callback, times(0)).onError(any())
        }, 2000L)
    }

}