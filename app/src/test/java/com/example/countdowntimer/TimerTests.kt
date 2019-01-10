package com.example.countdowntimer

import android.os.Handler
import com.example.countdowntimer.app.timer.Timer
import com.example.countdowntimer.timer.*
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
        var exceptionRaised = false

        try {
            timer.increaseTimer(10 * 1000L)
        }catch (ex: TimerNotStartedException){
            exceptionRaised = true
        }

        assert(exceptionRaised)
    }

    @Test
    fun `should raise TimerMaxLimitReachedException on increaseTime when not able to increase more than max time`() {
        var exceptionRaised = false

        timer.start(startTime, MAX_TIME, mock())

        try {
            timer.increaseTimer(1000L)
        }catch (ex: TimerMaxLimitReachedException){
            exceptionRaised = true
        }
        timer.stop()

        assert(exceptionRaised)
    }


    @Test
    fun `should not raise any TimerException when increaseTime is able to increment time`() {
        var exceptionRaised = true

        timer.start(startTime, MAX_TIME, mock())

        Handler().postDelayed({
            try {
                timer.increaseTimer(1000L)
            }catch (ex: TimerException){
                exceptionRaised = true
            }

            assert(!exceptionRaised)
        }, 2000L)
    }

}